// 扩展bootstrapValidator的validator
// 注册一个 serverError validator，用于对话框显示服务器返回的错误
;(function ($) {
    $.fn.bootstrapValidator.validators.serverError = {
        validate: function (validator, $field, options) {
            return true;
        }
    };
}(window.jQuery));

(function ($) {
    $.fn.bootstrapValidator.validators.mobilePhone = {
        validate: function (validator, $field, options) {
            var value = $field.val();
            if (value == '') {
                return true;
            }
            return isPhoneValid(value);
        }
    };
}(window.jQuery));


// 使用简短名称
var Dialog = BootstrapDialog;

// 打开的对话框对象栈
Dialog.dialogStack = [];

/**
 * 获取当前对话框的父对话框上下文
 * @returns {*}
 */
Dialog.getParentContext = function () {
    var length = Dialog.dialogStack.length;
    if (length > 1) { // 打开的对话框超过两个
        return $(Dialog.dialogStack[length - 2].getModal()); // 返回上一个对话框的上下文
    }
    return getContext(); // right-content
};

/**
 * 获取dialog 对话框的上下文
 * @returns {*}
 */
Dialog.getContext = function () {
    var dlg = Dialog.get();
    if (dlg == null) {
        return getContext();
    }
    return $(dlg.getModalDialog());
};

/**
 * 获取当前打开的bootstrap dialog对象
 * @returns {*}
 */
Dialog.get = function () {
    var length = Dialog.dialogStack.length;
    if (length > 0) {
        return Dialog.dialogStack[length - 1];
    }
    return null;
};

/**
 * 回调函数队列
 */
Dialog.callBack = $.Callbacks("once memory");

/**
 *  动态绑定所有a连接的按钮，打开对话框
 */
$('body').on('click', '.dialog', function (event) {
    event.preventDefault();// 禁止a连接的href事件
    var $this = $(this);
    var options, url = $this.attr('href') || $this.data('url'), title = $this.data('title') || $this.attr('title'), reloadBtn = $this.data('reload-btn');
    // 打開確認框
    if ($this.hasClass('dialog-confirm')) {
        options = {
            title: title,
            message: $this.data('message'),
            callback: function (res) {
                if (res) {
                    // 發起動作請求
                    $.post(url, function () {
                        if (reloadBtn) {
                            Dialog.getParentContext().find(reloadBtn).trigger('click'); // 触发重新查询事件
                        }
                    });
                }
            }
        };
        Dialog.confirm(options);
    } else {
        // 打開默認對話框
        if (url) {
            options = {
                title: title,
                url: url,
                width: $this.data('width'),
                urlData: null,
                reloadBtn: reloadBtn
            };
            Dialog.create(options);
        } else {
            console.info('Dialog do not support this element');
            console.info($this);
        }

    }
});

$('body').on('click', '.cleanBtn', function (event) {
    event.preventDefault(); // 禁止a连接的href事件
    event.stopPropagation();
    var $this = $(this);
    $this.parents('.input-group').find('input').val(''); // 清空内容
});

/**
 * js 快捷打开对话框
 *
 * @param title
 * @param url
 */
Dialog.create = function (opts) {
    $.ajax({
        url: opts.url,
        type: "post",
        dataType: "html",
        data: opts.urlData,
        success: function (message) {
            // ajax json 处理
            try {
                var json = $.parseJSON(message);
                if (json.statusCode == 401) {
                    Dialog.alert({
                        title: "Access denied",
                        message: json.message
                    });
                } else if (json.statusCode == 301) {
                    Dialog.alert({
                        title: "Timeout",
                        message: json.message,
                        callback: function () {
                            window.location.href = json.forward;
                        }
                    });
                }
            } catch (err) {
                // 打开对话框，显示请求的内容
                var options = {
                    title: opts.title,
                    message: $('<div/>').html(message),
                    draggable: true,
                    closeByBackdrop: false, // 不允許點擊modal-backdrop關閉窗口
                    onshow: function (selfDlg) {
                        Dialog.dialogStack.push(selfDlg);

                    },
                    onshown : function() {
                        // bootstrap中使用modal加载kindeditor时弹出层文本框不能输入的问题
                        //加上下面这句！解决了~
                        $(document).off('focusin.modal');
                    },
                    onhidden: function (selfDlg) {
                        Dialog.dialogStack.pop();
                        if (typeof opts.callback === 'function') {
                            opts.callback(selfDlg); // 回到函数
                        }
                    }
                };
                var dlg = Dialog.show(options); // 打开对话框
                if (opts.width) {
                    dlg.$modal.find('div.modal-dialog').width(opts.width); // 设置modal 的宽度
                }
            }
        }
    });
};

// 对话框提交事件
$('body').on('click', 'a.dialogSubmitBtn,button.dialogSubmitBtn', function (event) {
    event.preventDefault();// 禁止a连接的href事件
    var submitBtn = $(this);
    var form = submitBtn.parents('form');

    // 提交前运行特殊的方法
    var beforeSubmitEvent = form.data('beforeSubmitEvent');
    if(typeof beforeSubmitEvent  === 'function') {
        beforeSubmitEvent(form);
    }

    // 跳过验证
    if (!submitBtn.data('skip-validate')) {
        var bootstrapValidator = form.data('bootstrapValidator');
        if (bootstrapValidator) {
            // 自定义验证
            if (typeof bootstrapValidator.options.customValidate === 'function' && !bootstrapValidator.options.customValidate()) {
                return;
            }
            bootstrapValidator.validate(); // 验证
            var result = bootstrapValidator.isValid(); // 取出结果
            if (!result) {
                return;
            }
        }
    }
    
    // 在提交之前执行自定义回到函数
    if(typeof formDialogBeforeSubmit === 'function') {
    	formDialogBeforeSubmit(form);
    }
    ajaxFormSubmit({
        url: form.attr('action'),
        form: form,
        submitBtn: submitBtn
    });
});

// 对话框中的reset按钮事件
$('body').on('click', 'a.dialogResetBtn,button.dialogResetBtn', function (event) {
    event.preventDefault();// 禁止a连接的href事件
    var form = $(this).parents('form');
    form.data('bootstrapValidator').resetForm(true);
});



function getMenuContext() {
    return $('#menuContext');
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

// ------------------------------------------------------------------
// getDateFromFormat( date_string , format_string )
//
// This function takes a date string and a format string. It matches
// If the date string matches the format string, it returns the
// getTime() of the date. If it does not match, it returns 0.
// ------------------------------------------------------------------
function getDateFromFormat(val,format) {
    val=val+"";
    format=format+"";
    var i_val=0;
    var i_format=0;
    var c="";
    var token="";
    var x,y;
    var now=new Date();
    var year=now.getYear();
    var month=now.getMonth()+1;
    var date=1;
    var hh=now.getHours();
    var mm=now.getMinutes();
    var ss=now.getSeconds();
    var ampm="";

    while (i_format < format.length) {
        // Get next token from format string
        c=format.charAt(i_format);
        token="";
        while ((format.charAt(i_format)==c) && (i_format < format.length)) {
            token += format.charAt(i_format++);
        }
        // Extract contents of value based on format token
        if (token=="yyyy" || token=="yy" || token=="y") {
            if (token=="yyyy") { x=4;y=4; }
            if (token=="yy")   { x=2;y=2; }
            if (token=="y")    { x=2;y=4; }
            year=_getInt(val,i_val,x,y);
            if (year==null) { return 0; }
            i_val += year.length;
            if (year.length==2) {
                if (year > 70) { year=1900+(year-0); }
                else { year=2000+(year-0); }
            }
        }
        else if (token=="MMM"||token=="NNN"){
            month=0;
            for (var i=0; i<MONTH_NAMES.length; i++) {
                var month_name=MONTH_NAMES[i];
                if (val.substring(i_val,i_val+month_name.length).toLowerCase()==month_name.toLowerCase()) {
                    if (token=="MMM"||(token=="NNN"&&i>11)) {
                        month=i+1;
                        if (month>12) { month -= 12; }
                        i_val += month_name.length;
                        break;
                    }
                }
            }
            if ((month < 1)||(month>12)){return 0;}
        }
        else if (token=="EE"||token=="E"){
            for (var i=0; i<DAY_NAMES.length; i++) {
                var day_name=DAY_NAMES[i];
                if (val.substring(i_val,i_val+day_name.length).toLowerCase()==day_name.toLowerCase()) {
                    i_val += day_name.length;
                    break;
                }
            }
        }
        else if (token=="MM"||token=="M") {
            month=_getInt(val,i_val,token.length,2);
            if(month==null||(month<1)||(month>12)){return 0;}
            i_val+=month.length;}
        else if (token=="dd"||token=="d") {
            date=_getInt(val,i_val,token.length,2);
            if(date==null||(date<1)||(date>31)){return 0;}
            i_val+=date.length;}
        else if (token=="hh"||token=="h") {
            hh=_getInt(val,i_val,token.length,2);
            if(hh==null||(hh<1)||(hh>12)){return 0;}
            i_val+=hh.length;}
        else if (token=="HH"||token=="H") {
            hh=_getInt(val,i_val,token.length,2);
            if(hh==null||(hh<0)||(hh>23)){return 0;}
            i_val+=hh.length;}
        else if (token=="KK"||token=="K") {
            hh=_getInt(val,i_val,token.length,2);
            if(hh==null||(hh<0)||(hh>11)){return 0;}
            i_val+=hh.length;}
        else if (token=="kk"||token=="k") {
            hh=_getInt(val,i_val,token.length,2);
            if(hh==null||(hh<1)||(hh>24)){return 0;}
            i_val+=hh.length;hh--;}
        else if (token=="mm"||token=="m") {
            mm=_getInt(val,i_val,token.length,2);
            if(mm==null||(mm<0)||(mm>59)){return 0;}
            i_val+=mm.length;}
        else if (token=="ss"||token=="s") {
            ss=_getInt(val,i_val,token.length,2);
            if(ss==null||(ss<0)||(ss>59)){return 0;}
            i_val+=ss.length;}
        else if (token=="a") {
            if (val.substring(i_val,i_val+2).toLowerCase()=="am") {ampm="AM";}
            else if (val.substring(i_val,i_val+2).toLowerCase()=="pm") {ampm="PM";}
            else {return 0;}
            i_val+=2;}
        else {
            if (val.substring(i_val,i_val+token.length)!=token) {return 0;}
            else {i_val+=token.length;}
        }
    }
    // If there are any trailing characters left in the value, it doesn't match
    if (i_val != val.length) { return 0; }
    // Is date valid for month?
    if (month==2) {
        // Check for leap year
        if ( ( (year%4==0)&&(year%100 != 0) ) || (year%400==0) ) { // leap year
            if (date > 29){ return 0; }
        }
        else { if (date > 28) { return 0; } }
    }
    if ((month==4)||(month==6)||(month==9)||(month==11)) {
        if (date > 30) { return 0; }
    }
    // Correct hours value
    if (hh<12 && ampm=="PM") { hh=hh-0+12; }
    else if (hh>11 && ampm=="AM") { hh-=12; }
    return new Date(year,month-1,date,hh,mm,ss);
}

// ------------------------------------------------------------------
// Utility functions for parsing in getDateFromFormat()
// ------------------------------------------------------------------
function _isInteger(val) {
    var digits="1234567890";
    for (var i=0; i < val.length; i++) {
        if (digits.indexOf(val.charAt(i))==-1) { return false; }
    }
    return true;
}
function _getInt(str,i,minlength,maxlength) {
    for (var x=maxlength; x>=minlength; x--) {
        var token=str.substring(i,i+x);
        if (token.length < minlength) { return null; }
        if (_isInteger(token)) { return token; }
    }
    return null;
}

// 注册time的按钮click事件
$(function () {
    $('body').on('click', '.time-toggle', function () {
        $(this).siblings('input').trigger('focus');
    });
});


function serviceValidateCallBack(json, form) {
    switch (json.statusCode) {
        case 200:
        	var callback = form.data('callback');
            Dialog.get().close(); // 关闭对话框
            Dialog.success(json.message, function () {
                $('a.search-btn', getContext()).trigger('click');
                if(callback) {
                     if(typeof callback === 'object' && typeof callback.afterSuccess === 'function') {
                     	callback.afterSuccess();
                     }
                }
               
            });
            break;
        case 300:
            // 验证错误处理
            if (json.errorFields.length > 0) {
                var fieldErrors = '';
                $.each(json.errorFields, function (index, item) {
                    var fieldName = item.fieldName;
                    var errorMessage = item.errorMessage;
                    fieldErrors += fieldName + ": " + errorMessage + "<br/>";
                });
                if (fieldErrors) {
                    Dialog.alert({
                        title: "ERROR",
                        message: fieldErrors
                    });
                }
            } else if (json.alertErrorMsg) {
                // 其他弹窗错误
                Dialog.alert({
                    title: "ERROR",
                    message: json.alertErrorMsg
                });
            }
            if (form) {
                form.data('form-token', json.form_token);  // 重新设置token的值
            }
            break;
        case 301:
            Dialog.alert({
                title: "Timeout",
                message: json.message,
                callback: function () {
                    window.location.href = json.forward;
                }
            });
            break;
        case 401:
            Dialog.alert({
                title: "Access denied",
                message: json.message
            });
            break;
        default:
            console.info("status code error:" + json.statusCode);
            break;
    }
}

// 表单提交方法 参数{url:'', form:'', submitBtn:''}
function ajaxFormSubmit(options) {
    options.submitBtn.button('loading');
    var data;
    var form = options.form;
    var token = form.data('form-token');
    var dynamicFormTokenElement = false;
    if (token && form.find('input[name=form_token]').length == 0) {
        //data += '&form_token=' + token;
        dynamicFormTokenElement = true;
        form.append('<input name="form_token" type="hidden" value="' + token + '">');

    } else {
        console.info("This form has not set data-form-token!");
    }

    console.info(form.attr('enctype'));
    if ('multipart/form-data' == form.attr('enctype')) {
        console.info(form[0]);
        data = new FormData(form[0]);
        $.ajax({
            url: options.url,
            type: "POST",
            data: data,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (json) {
                serviceValidateCallBack(json, options.form);
            }
        }).always(function () {
            options.submitBtn.button('reset');
            if (dynamicFormTokenElement) {
                form.find('input[name=form_token]').remove();
            }
        });
    } else {
        data = form.serialize();
        $.ajax({
            url: options.url,
            type: "POST",
            data: data,
            dataType: 'json',
            success: function (json) {
                serviceValidateCallBack(json, options.form);
            }
        }).always(function () {
            options.submitBtn.button('reset');
            if (dynamicFormTokenElement) {
                form.find('input[name=form_token]').remove();
            }
        });
    }
}


/**
 * 遮罩层Loading框
 *
 * @type {{show, hide}}
 */
var maskLoadingDlg = (function () {
    var loadingDlg = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>Loading ... </h1></div><div class="modal-body"><div class="progress progress-striped active"></div></div></div>');
    return {
        show: function () {
            loadingDlg.modal();
        },
        hide: function () {
            loadingDlg.modal('hide');
        }
    };
})();


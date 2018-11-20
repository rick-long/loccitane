/**
 * Created by hary on 2016/8/24.
 */

function loadFromPage(options) {
    var parent = getContext();
    parent.mask('Loading ... ');
    $.ajax({
        url: options.url,
        type: "post",
        dataType: "html",
        data: options.data,
        success: function (response) {
            try {
                var json = $.parseJSON(response);
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
                parent.html($('<div class="form-content"></div>').html(response));
                var formContent = $('.form-content', parent);
                //formContent.width(parent.width()); // 计算page的宽度
                var height = $(window).height() - Math.ceil(parent.offset().top) - 50;
                formContent.height(height);
            }
        }
    }).always(function () {
        parent.unmask();
    });
}

$(function () {
    // 动态绑定打开form页面
    $('body').on('click', '.form-page', function (event) {
        event.preventDefault();// 禁止a连接的href事件
        var $this = $(this);
        var url = $this.attr('href') || $this.data('url'), title = $this.data('title') || $this.attr('title');

        loadFromPage({
            url: url,
            data: ''
        });
    });

    // Form 页面提交事件
    $('body').on('click', 'a.formPageSubmit,button.formPageSubmit', function (event) {
        event.preventDefault();// 禁止a连接的href事件
        var submitBtn = $(this);
        if (submitBtn.hasClass('doubleConfirm')) {
            // 再次确认
            Dialog.confirm({
                title: "Confirmed Dialog",
                message: "Sure to save?",
                callback: function (res) {
                    if (res) {
                        pageFormAction(submitBtn);
                    }
                }
            });
        } else {
            pageFormAction(submitBtn);
        }
    });

    function pageFormAction(submitBtn) {
        var form = submitBtn.parents('form');
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
        if (typeof formPageBeforeSubmit === 'function') {
            formPageBeforeSubmit(form);
        }

        submitBtn.button('loading');
        var data = form.serialize();
        var token = form.data('form-token');
        if (token) {
            data += '&form_token=' + token;
        } else {
            console.info("This form has not set data-form-token!");
        }
        $.ajax({
            url: form.attr('action'),
            type: "POST",
            data: data,
            dataType: 'json',
            success: function (json) {
                formPageSubmitCallBack(json, form);
            }
        }).always(function () {
            submitBtn.button('reset');
        });
    }

    function formPageSubmitCallBack(json, form) {
        switch (json.statusCode) {
            case 200:
                Dialog.success(json.message, function () {
                    if (form.data('forward')) {
                        $("#" + form.data('forward'), $("#menuContext")).trigger('click'); // 模拟点击菜单
                    } else {
                        console.info("form did not set data-forward attribute for redirect.");
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
});
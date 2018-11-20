// 获取当前的上下文方法
function getContext() {
    return $('#right-content');
}

// string 工具类
String.format = function () {
    if (arguments.length == 0)
        return null;
    var str = arguments[0];
    for (var i = 1; i < arguments.length; i++) {
        var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};

function getFormJson(frm) {
    if (typeof frm === 'string') {
        frm = $(frm);
    }
    var o = {};
    var a = frm.serializeArray();
    $.each(a, function () {
        //alert(this.name+"-"+this.value);
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

// 获取上下文根名称:loccitane
function getServletContextPath() {
    return globalServletContext;
}

// 注册multi menu初始化事件
$(function () {

    $('body').on('mouseover', 'a.multi-link', function (event) {
        event.preventDefault();// 禁止a连接的href事件
        var $this = $(this);
        var parentUL = $this.parents('ul');
        var multiMenu = parentUL.parents('.multi-menu');
        var menuArea = parentUL.parents('.menu-area');
        parentUL.find('li > a').removeClass('selected');
        $this.addClass('selected');
        var left = parentUL.position().left + parentUL.outerWidth();
        var cloneUL = $this.siblings('ul').clone(false).css({left: left + 'px'}).removeClass('hide');
        if (!$this.hasClass('leaf')) {
            parentUL.nextAll('ul').remove();
            cloneUL.appendTo(menuArea);
            var width = 0;
            menuArea.children('ul').each(function () {
                width += $(this).outerWidth();
            });
            menuArea.width(width);
        }
    });

    $('body').on('click', 'a.multi-link', function (event) {
        event.preventDefault();// 禁止a连接的href事件
        var $this = $(this);
        var parentUL = $this.parents('ul');
        parentUL.find('li > a').removeClass('selected');
        $this.addClass('selected');
        if (parentUL.hasClass('selectable') || $this.hasClass('leaf')) {
            var multiMenu = parentUL.parents('.multi-menu');
            var menuArea = parentUL.parents('.menu-area');
            multiMenu.find('input.select-id').val($this.data('id')); // 回填id
            multiMenu.find('input.show').val($this.data('display-name')).trigger('change'); // 回填 displayName
            menuArea.addClass('hide');
        }
    });

    $.fn.multiMenu = function (options) {
        return $(this).each(function () {
            var $self = $(this);
            $self.find('input.show').click(function () {
                var menuArea = $self.find('.menu-area');
                if (menuArea.hasClass('hide')) {
                    menuArea.removeClass('hide');
                    var children = menuArea.children('ul');
                    var width = 0;
                    children.each(function () {
                        width += $(this).outerWidth();
                    });
                    menuArea.width(width);
                    // 固定位置
                    //menuArea.offset({top: $inputShow.position().top + $inputShow.height(), left: $inputShow.position().left});
                } else {
                    menuArea.addClass('hide');
                }
            });
        });
    };

    $.fn.multiMenuV2 = function (options) {
        return $(this).each(function () {
            var $self = $(this);
            $self.find('input.show').click(function () {
                var menuArea = $self.find('.menu-area');
                if (menuArea.hasClass('hide')) {
                    menuArea.removeClass('hide');
                    var children = menuArea.children('ul');
                    var width = 0;
                    children.each(function () {
                        width += $(this).outerWidth();
                    });
                    menuArea.width(width);
                    // 固定位置
                    //menuArea.offset({top: $inputShow.position().top + $inputShow.height(), left: $inputShow.position().left});
                } else {
                    menuArea.addClass('hide');
                }
            });
        });
    };
});


// drop down menu mouse over 事件
function dropdownMenuLoadSubMenu(element) {
    var $this = $(element);
    var parent = $this.parent('li');
    var parentUL = $this.parents('ul');
    var dropDownArea = $this.parents('div.dropdown');
    var dropdownContext = dropDownArea.parent();
    // 异步加载子节点, 没有加载
    if (parent.hasClass('dropdown-submenu')) {
        var cache = dropdownContext.data('cache');
        if (!cache) {
            cache = {};
            dropdownContext.data('cache', cache);
        }

        // 计算查找参数
        var param = {
            selectable: dropdownContext.data('selectable')
        };
        var categoryId = $this.data('category-id');
        var productId = $this.data('product-id');
        var productOptionId = $this.data('product-option-id');
        var isOnline = $this.data('is-online');
        var shopId =  $this.data('shop-id');
        if (categoryId) {
            param.rootId = categoryId;
            param.isOnline=isOnline;
            param.shopId=shopId;
            param.level = $this.data('children') ? 'category' : 'product';
        } else if (productId) {
            param.rootId = productId;
            param.isOnline=isOnline;
            param.shopId=shopId;
            param.level = 'productOption';
        } else {
            param.rootId = productOptionId;
            param.level = 'productOption';
        }

        param.deep = Number(parentUL.data('deep')) + 1; // 设置深度

        var key = param.level + "_" + param.rootId + "_" + param.deep;
        var cachedUl = cache[key];
        if (cachedUl) {
            var currentDeep = parentUL.data('deep');
            var uls = dropDownArea.find('> ul');
            uls.each(function () {
                var $item = $(this);
                var itemDeep = $item.data('deep');
                if (itemDeep > 0 && itemDeep > currentDeep) {
                    $item.remove();
                }
            });
            cachedUl.clone().appendTo(dropDownArea);
        } else {
            $.post(getServletContextPath() + '/category/categoryMenu', param, function (res) {
                console.info(parent);
                var $res = $(res);
                //$res.appendTo(parent);
                cache[key] = $res;  // 保存

                var currentDeep = parentUL.data('deep');
                var uls = dropDownArea.find('> ul');
                console.info(uls);
                uls.each(function () {
                    var $item = $(this);
                    var itemDeep = $item.data('deep');
                    if (itemDeep > 0 && itemDeep > currentDeep) {
                        console.info($item);
                        $item.remove();
                    }
                });
                $res.clone().appendTo(dropDownArea);
            });
        }
    } else {
        // 关闭其他的打开的子菜单
        parent.siblings('li').removeClass('open');
        parent.addClass('open');
    }

    return false;
}


function dropdownMenuClick(element, event) {
    var $this = $(element);
    var dropdownArea = $this.parents('div.dropdown');
    var dropdownContext = $this.parents('div.dropdown').parent();

    var selectable = dropdownContext.data('selectable');
    var categoryId = dropdownContext.find('input[name=categoryId]');
    var productId = dropdownContext.find('input[name=productId]');
    var productOptionId = dropdownContext.find('input[name=productOptionId]');
    var displayName = dropdownContext.find('input[name=displayName]');


    if ($this.hasClass('category') && selectable.indexOf('category') > -1) {
        categoryId.val($this.data('category-id'));
        displayName.val($this.data('display-name'));
        productId.val("");
        productOptionId.val("");
        displayName.trigger('change'); // 触发更改事件
        dropdownArea.find("> ul:gt(0)").addClass("hide"); // 隐藏
        return false;
    } else if ($this.hasClass('product') && selectable.indexOf('product') > -1) {
        categoryId.val($this.data('category-id'));
        productId.val($this.data('product-id'));
        categoryId.val($this.data(''));
        productOptionId.val("");
        displayName.val($this.data('display-name'));
        displayName.trigger('change'); // 触发更改事件
        dropdownArea.find("> ul:gt(0)").addClass("hide"); // 隐藏
        return false;
    } else if ($this.hasClass('productOption') && selectable.indexOf('option') > -1) {
        categoryId.val('');
        productId.val('');
        productOptionId.val($this.data('product-option-id'));
        displayName.val($this.data('display-name'));
        displayName.data('duration', $this.data('duration')); // 附加duration
        displayName.data('process-time', $this.data('process-time')); // 附加process time
        displayName.data('capacity', $this.data('capacity'));
        displayName.data('product-id', $this.data('product-id')); // 附加product-id
        displayName.trigger('change'); // 触发更改事件
        dropdownArea.find("> ul:gt(0)").addClass("hide"); // 隐藏
        return false;
    }

    event.stopPropagation();
    return false;
}

// a 注册loadPage事件
$(function () {
    getContext().on('click', 'a.loadPage', function (e) {
        e.preventDefault();
        var menu = $(this);
        var parent = getContext();
        parent.mask('Loading ... ');
        $.ajax({
            url: menu.attr('href'),
            type: "post",
            dataType: "html",
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
                    parent.html(response).find(".search-btn").trigger("click");
                }
            }
        }).always(function () {
            parent.unmask();
        });

        return false;
    });
});

// 验证email
var _emailRegExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

function isEmailValid(value) {
    return _emailRegExp.test(value);
}

// 验证phone
var _phoneRegExp = new RegExp('^[0-9]*$');
function isPhoneValid(value) {
    return _phoneRegExp.test(value);
}

/**
 * 注册input框enter事件处理
 */
$(function () {
    $('body').on('keydown', 'form input', function (event) {
        // enter的事件
        if (event.keyCode == 13) {
            event.preventDefault();
            var form = $(this).parents('form');
            var searchBtn = form.find('.search-btn');
            if (searchBtn.length == 1) {
                searchBtn.trigger('click');
                return false;
            }
            var dialogSubmitBtn = form.find('.dialogSubmitBtn');
            if (dialogSubmitBtn.length == 1) {
                dialogSubmitBtn.trigger('click');
                return false;
            }
            return false;
        }
    });
});
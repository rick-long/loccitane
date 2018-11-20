// 选择目录插件
(function ($) {

    // 第一版删除
    /*$.fn.selectCategory = function (options) {
        var categoryPlugin = $(this);
        return categoryPlugin.each(function () {
            var $this = $(this);
            var param = {
                rootId: $this.data('root-id'),
                selectable: $this.data('selectable'),
                categoryId: $this.data('category-id'),
                productId: $this.data('product-id'),
                productOptionId: $this.data('product-option-id')
            };
            $.post(getServletContextPath() + "/category/categorySelect", param, function (res) {
                $this.html(res);
                $('[data-submenu]', $this).submenupicker();
                var selectable = $this.data('selectable');
                var categoryId = $this.find('input[name=categoryId]');
                var productId = $this.find('input[name=productId]');
                var productOptionId = $this.find('input[name=productOptionId]');
                var displayName = $this.find('input[name=displayName]');
                // 点击目录
                $this.find('a').click(function () {
                    var $self = $(this);
                    categoryId.val('');
                    productId.val('');
                    productOptionId.val('');
                    if ($self.hasClass('category') && selectable.indexOf('category') > -1) {
                        categoryId.val($self.data('categoryId'));
                        displayName.val($self.data('display-name'));
                    } else if ($self.hasClass('product') && selectable.indexOf('product') > -1) {
                        productId.val($self.data('productId'));
                        displayName.val($self.data('display-name'));
                    } else if ($self.hasClass('productOption') && selectable.indexOf('option') > -1) {
                        productOptionId.val($self.data('productOptionId'));
                        displayName.val($self.data('display-name'));
                        displayName.data('duration', $self.data('duration')); // 附加duration
                        displayName.data('process-time', $self.data('process-time')); // 附加duration
                        displayName.data('capacity', $self.data('capacity'));
                    }
                    displayName.trigger('change'); // 触发更改事件
                });

                if( typeof options.callback === 'function') {
                    options.callback();
                }
            });
        });
    };*/

    // 第二版 异步分级加载
    $.fn.selectCategory = function (options) {
        var categoryPlugin = $(this);
        return categoryPlugin.each(function () {
            var $this = $(this);
            var param = {
                rootId: $this.data('root-id'),
                rootId1: $this.data('root-id1'),
                selectable: $this.data('selectable'),
                categoryId: $this.data('category-id'),
                productId: $this.data('product-id'),
                productOptionId: $this.data('product-option-id'),
                isOnline: $this.data('is-online'),
                shopId: $this.data('shop-id'),
                deep: 0
            };
            $.post(getServletContextPath() + "/category/categorySelect2", param, function (res) {
                $this.html(res);
                if (typeof options.callback === 'function') {
                    options.callback($this);
                }

                if (typeof options.click === 'function') {
                    $this.on('click', 'a', options.click);
                }
            });
        });
    };

})(jQuery);
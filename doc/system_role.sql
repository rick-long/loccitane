
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (1, 'ADMIN', 'Admin', 1, '1', '2016-05-20 17:36:04', 'ibsadmin', '2016-05-26 10:38:54', 'ibsadmin');
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (2, 'THERAPIST', 'Therapist', 1, '1', '2016-05-25 11:12:23', 'admin', '2016-05-25 11:12:23', 'admin');
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (3, 'SHOP_MANAGER', 'Shop Manager', 1, '1', '2016-05-25 11:18:25', 'admin', '2016-05-25 11:18:25', 'admin');
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (4, 'RECEPTION', 'Reception', 1, '1', '2016-05-25 11:20:10', 'admin', '2016-05-25 17:53:12', 'admin');

INSERT INTO `loccitane`.`sys_user_role` (`user_id`, `role_id`) VALUES (2, 1);

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('1', NULL, '0', '1', 'root', 'Root', '', 'root');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('10000', '1', '1', '2', 'module', 'Shop', '', 'shopModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('10100', '10000', '0', '3', 'function', 'Room List', NULL, 'room:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('10200', '10000', '0', '4', 'function', 'Shop List', NULL, 'shop:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('11000', '1', '2', '5', 'module', 'Member', NULL, 'memberModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('11100', '11000', '0', '6', 'function', 'Member List', NULL, 'member:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('12000', '1', '3', '7', 'module', 'Staff', NULL, 'staffModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('12100', '12000', '0', '8', 'function', 'Staff List', NULL, 'staff:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13000', '1', '4', '11', 'module', 'Product', NULL, 'productModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13100', '13000', '0', '12', 'function', 'Category List', NULL, 'category:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13200', '13000', '0', '13', 'function', 'Product List', NULL, 'product:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13300', '13000', '0', '14', 'function', 'Supplier List', NULL, 'supplier:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13500', '13000', '0', '16', 'function', 'Brand List', NULL, 'brand:toView,list');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14000', '1', '5', '18', 'module', 'Book', NULL, 'bookModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14100', '14000', '0', '19', 'function', 'Book List', NULL, 'book:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14200', '14000', '0', '20', 'function', 'Room View', NULL, 'book:bookTimeRoomView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14300', '14000', '0', '21', 'function', 'Therapist View', NULL, 'book:bookTimeTherapistView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('15000', '1', '6', '22', 'module', 'Sales', NULL, 'salesModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('15100', '15000', '0', '23', 'function', 'Sales List', NULL, 'sales:toView,listOrder');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16000', '1', '7', '24', 'module', 'Prepaid', NULL, 'prepaidModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16100', '16000', '0', '25', 'function', 'Prepaid List', NULL, 'prepaid:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17000', '1', '8', '26', 'module', 'Inventory', NULL, 'inventoryModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17100', '17000', '0', '27', 'function', 'Inventory Transaction List', NULL, 'inventory:transactionManagement,transactionList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17200', '17000', '0', '28', 'function', 'Inventory Purchase Order List', NULL, 'inventory:purchaseOrderManagement,purchaseOrderList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17300', '17000', '0', '29', 'function', 'Inventory List', NULL, 'inventory:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('18000', '1', '9', '30', 'module', 'Loyalty', NULL, 'loyaltyModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('18100', '18000', '0', '31', 'function', 'Spa Points List', NULL, 'sp:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('18110', '18100', '0', '31', 'function', 'Adjust', NULL, 'sp:toAdjust,adjust');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('18200', '18000', '0', '32', 'function', 'Loyalty Points List', NULL, 'lp:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('18210', '18200', '0', '31', 'function', 'Adjust', NULL, 'lp:toAdjust,adjust');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('18300', '18000', '0', '33', 'function', 'Loyalty Level List', NULL, 'loyalty:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('19000', '1', '10', '34', 'module', 'Discount', NULL, 'discountModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('19200', '19000', '0', '36', 'function', 'Discount Rule List', NULL, 'discount:toRuleView,discountRuleList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('20000', '1', '11', '37', 'module', 'System Configulation', NULL, 'sysCfgModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('21000', '20000', '0', '38', 'function', 'System Role Management', NULL, 'sysRole:sysRoleManagement,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('21100', '21000', '0', '39', 'function', 'Add', NULL, 'sysRole:sysRoleAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('21200', '21000', '0', '40', 'function', 'Edit', NULL, 'sysRole:sysRoleEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('21300', '21000', '0', '41', 'function', 'Permission Assign', NULL, 'sysRole:sysRolePermissionAssign,assign');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12200', '12000', 'function', 'Staff Attribute Key List', 'staffAttrKey:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('10110', '10100', 'function', 'Add', 'room:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('10120', '10100', 'function', 'Edit', 'room:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('10210', '10200', 'function', 'Add', 'shop:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('10220', '10200', 'function', 'Edit', 'shop:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11110', '11100', 'function', 'Add', 'member:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11120', '11100', 'function', 'Edit', 'member:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12110', '12100', 'function', 'Add', 'staff:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12120', '12100', 'function', 'Edit', 'staff:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12210', '12200', 'function', 'Add', 'staffAttrKey:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12220', '12200', 'function', 'Edit', 'staffAttrKey:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13110', '13100', '0', '145', 'function', 'Add', NULL, 'category:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13120', '13100', '0', '146', 'function', 'Edit', NULL, 'category:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13210', '13200', '0', '138', 'function', 'Add', NULL, 'product:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13220', '13200', '0', '139', 'function', 'Edit', NULL, 'product:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13230', '13200', '0', '140', 'function', 'Add Supernumerary Price', NULL, 'po:toAddSupernumeraryPrice,addSupernumeraryPrice');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13310', '13300', '0', '133', 'function', 'Add', NULL, 'supplier:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13320', '13300', '0', '132', 'function', 'Edit', NULL, 'supplier:toEdit,edit');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13510', '13500', '0', '142', 'function', 'Add', NULL, 'brand:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13520', '13500', '0', '143', 'function', 'Edit', NULL, 'brand:toEdit,edit');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14110', '14100', '0', '170', 'function', 'Add', NULL, 'book:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14120', '14100', '0', '171', 'function', 'Book Detail View', NULL, 'book:bookView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14130', '14100', '0', '172', 'function', 'Book Cancel', NULL, 'book:cancel');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14140', '14100', '0', '173', 'function', 'Book Check Out', NULL, 'sales:bookToCheckout');
-- 2017-10-27 by jason  --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14150', '14100', '0', '174', 'function', 'Book client Detail View', NULL, 'book:clientView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('14160', '14100', '0', '175', 'function', 'Product View', NULL, 'book:productView');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('15110', '15100', '0', '135', 'function', 'Add', NULL, 'sales:toAddSales');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('15120', '15100', '0', '136', 'function', 'Show Item Details', NULL, 'sales:showItemDetails');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('15130', '15100', '0', '136', 'function', 'Print Invoice', NULL, 'sales:print');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('15140', '15100', '0', '135', 'function', 'Delete Sales', NULL, 'sales:remove');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16110', '16100', '0', '135', 'function', 'Add', NULL, 'prepaid:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16120', '16100', '0', '136', 'function', 'Edit', NULL, 'prepaid:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16130', '16100', '0', '135', 'function', 'Top Up', NULL, 'prepaid:toTopUp,topUp');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16140', '16100', '0', '136', 'function', 'Show TopUp Transaction', NULL, 'prepaid:showTopUpTransaction');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16150', '16100', '0', '135', 'function', 'Delete', NULL, 'prepaid:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16160', '16100', '0', '136', 'function', 'Delete Transaction', NULL, 'prepaid:deleteTopUpTransaction');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16170', '16100', '0', '136', 'function', 'Print Voucher', NULL, 'prepaid:printvoucher');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16180', '16100', '0', '135', 'function', 'Show Used History', NULL, 'prepaid:usedHistory');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17110', '17100', '0', '203', 'function', 'Inventory Transaction Add', NULL, 'inventory:transactionToAdd,transactionAdd');
-- 2017-10-25 by jason  --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17120', '17100', '0', '203', 'function', 'inventory Details Export', NULL, 'inventory:inventoryDetailsExport,inventoryDetailsExport');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17210', '17200', '0', '198', 'function', 'Inventory Purchase Order Add', NULL, 'inventory:purchaseOrderToAdd,purchaseOrderAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17220', '17200', '0', '199', 'function', 'Inventory Purchase Order Detail View', NULL, 'inventory:purchaseOrderView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17230', '17200', '0', '200', 'function', 'Inventory Purchase Order Shipment Add', NULL, 'inventory:purchaseOrderToShipmentAdd,purchaseOrderShipmentAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17240', '17200', '0', '201', 'function', 'Inventory Print', NULL, 'inventory:print');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('17310', '17300', '0', '0', 'function', 'Transfer Add', NULL, 'inventory:transferToAdd,transferAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('18310', '18300', '0', '216', 'function', 'Add', NULL, 'loyalty:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('18320', '18300', '0', '216', 'function', 'Edit', NULL, 'loyalty:toEdit,edit');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('19210', '19200', '0', '228', 'function', 'Discount Rule Add', NULL, 'discount:discountRuleToAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('19220', '19200', '0', '229', 'function', 'Discount Rule Detail View', NULL, 'discount:discountRuleView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('19230', '19200', '0', '230', 'function', 'Discount Rule Edit', NULL, 'discount:discountRuleToEdit,discountTemplateSave');

INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '1');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '10000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '10100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '10200');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '11000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '11100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '12000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '12100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '13000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '13100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '13200');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '13300');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '13500');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '14000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '14100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '14200');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '14300');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '15000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '15100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '16000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '16100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '17000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '17100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '17200');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '17300');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '18000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '18100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '18200');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '18300');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '19000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '19200');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '20000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '21000');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '21100');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '21200');
INSERT INTO `loccitane`.`sys_role_resource` (`role_id`, `resource_id`) VALUES ('1', '21300');

-- 2016-6-1 by Hary --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23000', '1', 'module', 'Marketing', 'marketingModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23100', '23000', 'function', 'Channel List', 'marketing:toMktChannelView,mktChannelList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23110', '23100', 'function', 'Add', 'marketing:toMktChannelAdd,mktChannelAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23120', '23100', 'function', 'Edit', 'marketing:toMktChannelEdit,mktChannelEdit');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23200', '23000', 'function', 'Mail Shot List', 'marketing:toMktMailShotView,mktMailShotList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23210', '23200', 'function', 'Add', 'marketing:toMktMailShotAdd,mktMailShotAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23220', '23200', 'function', 'Edit', 'marketing:toMktMailShotEdit,mktMailShotEdit');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23300', '23000', 'function', 'Email Template List', 'marketing:toMktEmailTemplateView,mktEmailTemplateList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23310', '23300', 'function', 'Add', 'marketing:toMktEmailTemplateAdd,mktEmailTemplateAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23320', '23300', 'function', 'Edit', 'marketing:toMktEmailTemplateEdit,mktEmailTemplateEdit');

-- 2016-6-7 by Hary marketing module --

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11200', '11000', 'function', 'User Group List', 'member:toGroupView,groupList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11210', '11200', 'function', 'Add', 'member:toGroupAdd,groupAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11220', '11200', 'function', 'Edit', 'member:toGroupEdit,groupEdit');


-- 2016-8-29 by Ivy for consent form --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11400', '11000', 'function', 'Consent Form List', 'consentForm:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11410', '11400', 'function', 'Add', 'consentForm:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11420', '11400', 'function', 'Edit', 'consentForm:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11430', '11400', 'function', 'Delete', 'consentForm:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11440', '11400', 'function', 'Sign', 'consentForm:toSign,sign');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11450', '11400', 'function', 'User View', 'consentForm:listUserConsentForm');


	
-- 2016-9-12 by Ivy for adding sales and booking to sales -
update `loccitane`.`sys_resource`  set `name`='Add Sales', `permission`='sales:salesToCheckOut' where id=15110;
update `loccitane`.`sys_resource`  set `name`='Book To Sales', `permission`='sales:bookToCheckout,bookItemToCheckout' where id=14140;

-- 2016-9-24 by Ivy -
update `loccitane`.`sys_resource`  set `permission`='sales:toView' where id=15100;

-- 2016-10-24 by Ivy move some template settings to system settings module -
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('26000', '1', 'module', 'System Settings', 'sysSettingsModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('26100', '26000', 'function', 'Fields Management for Sales', 'outSource:toTemplateView,outSourceTemplateList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('26110', '26100', 'function', 'Add', 'outSource:outSourceTemplateToAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('26120', '26100', 'function', 'Edit', 'outSource:outSourceTemplateToEdit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('26130', '26100', 'function', 'Delete', 'outSource:delete');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26200', '26000', '0', '17', 'function', 'Product Option Key List', NULL, 'pokey:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26210', '26200', '0', '135', 'function', 'Add', NULL, 'pokey:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26220', '26200', '0', '136', 'function', 'Edit', NULL, 'pokey:toEdit,edit');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26300', '26000', '0', '15', 'function', 'Product Description Key List', NULL, 'pdkey:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26310', '26300', '0', '148', 'function', 'Add', NULL, 'pdkey:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26320', '26300', '0', '149', 'function', 'Edit', NULL, 'pdkey:toEdit,edit');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26400', '26000', 'function', 'Payroll List', 'payroll:toTemplateView,payrollTemplateList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26410', '26400', 'function', 'Add', 'payroll:payrollTemplateToAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26420', '26400', 'function', 'Edit', 'payroll:payrollTemplateToEdit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26430', '26400', 'function', 'View', 'payroll:payrollTemplateView');


INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26500', '26000', 'function', 'Commission Template List', 'commission:toView,commissionTemplateList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26510', '26500', 'function', 'Template Add', 'commission:commissionTemplateToAdd,commissionTemplateConfirm,commissionTemplateSave');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26520', '26500', 'function', 'Template Edit', 'commission:commissionTemplateToEdit,commissionTemplateConfirm,commissionTemplateSave');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26530', '26500', 'function', 'Template Review', 'commission:commissionTemplateView');


INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26600', '26000', '0', '35', 'function', 'Discount Template List', NULL, 'discount:toView,discountTemplateList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26610', '26600', '0', '232', 'function', 'Discount Template Add', NULL, 'discount:discountTemplateToAdd,discountTemplateSave');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26620', '26600', '0', '233', 'function', 'Discount Template Detail View', NULL, 'discount:discountTemplateView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('26630', '26600', '0', '234', 'function', 'Discount Template Edit', NULL, 'discount:discountTemplateToEdit');
-- 2016-10-24 by Ivy add 2 role -
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) 
	VALUES (8, 'SHOP_MANAGER_T', 'Shop Manager T', 1, '1', now(), 'admin', now(), 'admin');
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) 
	VALUES (9, 'RECEPTION_T', 'Reception T', 1, '1', now(), 'admin', now(), 'admin');
	
	

-- 2016-6-16 by Ivy payroll modeule --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('24000', '1', 'module', 'Payroll', 'payrollModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('24100', '24000', 'function', 'Payroll List', 'payroll:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('24110', '24100', 'function', 'Add', 'payroll:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('24120', '24100', 'function', 'Edit', 'payroll:toEdit,edit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11130', '11100', 'function', 'Member Import', 'member:toImport,doImport');


-- 2016-7-22 by Ivy  --
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (5, 'OPERATIONAL_MANAGER', 'Operational Manager', 1, '1', '2016-05-25 11:20:10', 'admin', '2016-05-25 17:53:12', 'admin');
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (6, 'MARKETING', 'Marketing', 1, '1', '2016-05-25 11:20:10', 'admin', '2016-05-25 17:53:12', 'admin');
INSERT INTO `loccitane`.`sys_role` (`id`, `reference`, `name`, `company_id`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (7, 'HOLTEL_MANAGER', 'Hotel Manager', 1, '1', '2016-05-25 11:20:10', 'admin', '2016-05-25 17:53:12', 'admin');
	
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('10130', '10100', 'function', 'Delete', 'room:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('10230', '10200', 'function', 'Delete', 'shop:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12130', '12100', 'function', 'Delete', 'staff:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12230', '12200', 'function', 'Delete', 'staffAttrKey:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('13130', '13100', 'function', 'Delete', 'category:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('13240', '13200', 'function', 'Delete', 'product:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('13330', '13300', 'function', 'Delete', 'supplier:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('13530', '13500', 'function', 'Delete', 'brand:delete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('23130', '23100', 'function', 'Delete', 'marketing:mktChannelDelete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11230', '11200', 'function', 'Delete', 'member:userGroupDelete');

-- 2016-07-26 by Ivy commission module --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('25000', '1', 'module', 'Commission', 'commissionModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('25200', '25000', 'function', 'Commission Rule List', 'commission:toRuleView,commissionRuleList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('25210', '25200', 'function', 'Rule Add', 'commission:commissionRuleToAdd,commissionRuleConfirm,commissionRuleSave');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('25220', '25200', 'function', 'Rule Review', 'commission:commissionRuleView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('25230', '25200', 'function', 'Rule Edit', 'commission:commissionRuleToEdit,commissionRuleConfirm,commissionRuleSave');

-- 2016-7-22 by Ivy for communication channel --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11300', '11000', 'function', 'Communication Channel List', 'communicationChannel:toView,list');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11310', '11300', 'function', 'Communication Channel Add', 'communicationChannel:toAdd,add');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('11320', '11300', 'function', 'Communication Channel Edit', 'communicationChannel:toEdit,edit');


-- 2016-8-3 by Ivy for member/staff group -
update `loccitane`.`sys_resource`  set `name`='Member Group List', `permission`='member:toGroupView,groupList' where id=11200;
update `loccitane`.`sys_resource`  set `name`='Add', `permission`='member:toGroupAdd,groupAdd' where id=11210;
update `loccitane`.`sys_resource`  set `name`='Edit', `permission`='member:toGroupEdit,groupEdit' where id=11220;
update `loccitane`.`sys_resource`  set `permission`='member:groupDelete' where id=11230;

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12300', '12000', 'function', 'Staff Group List', 'staff:toGroupView,groupList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12310', '12300', 'function', 'Add', 'staff:toGroupAdd,groupAdd');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12320', '12300', 'function', 'Edit', 'staff:toGroupEdit,groupEdit');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12330', '12300', 'function', 'Delete', 'staff:groupDelete');


	
-- 2016-10-24 by Ivy add bunus template in system settings module --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26700', '26000', 'function', 'Bonus Template List', 'bonus:toView,bonusTemplateList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26710', '26700', 'function', 'Template Add', 'bonus:bonusTemplateToAdd,bonusTemplateConfirm,bonusTemplateSave');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26720', '26700', 'function', 'Template Edit', 'bonus:bonusTemplateToEdit,bonusTemplateConfirm,bonusTemplateSave');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) 
	VALUES ('26730', '26700', 'function', 'Template Review', 'bonus:bonusTemplateView');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('27000', '1', 'module', 'Bonus', 'bonusModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('27100', '27000', 'function', 'bonus Rule List', 'bonus:toRuleView,bonusRuleList');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('27110', '27100', 'function', 'Rule Add', 'bonus:bonusRuleToAdd,bonusRuleConfirm,bonusRuleSave');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('27120', '27100', 'function', 'Rule Review', 'bonus:bonusRuleView');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('27130', '27100', 'function', 'Rule Edit', 'bonus:bonusRuleToEdit,bonusRuleConfirm,bonusRuleSave');

-- 2017-2-20 --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('18400', '18000', '0', '0', 'function', 'Award Redemption List', NULL, 'awardRedemption:toView,list', '1', '2017-02-20 11:12:08', 'admin', '2017-02-20 11:12:08', 'admin');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('18410', '18400', '0', '0', 'function', 'Add', NULL, 'awardRedemption:toAdd,add', '1', '2017-02-20 11:20:34', 'admin', '2017-02-20 11:20:34', 'admin');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('18420', '18400', '0', '0', 'function', 'Edit', NULL, 'awardRedemption:toEdit,edit', '1', '2017-02-20 11:21:30', 'admin', '2017-02-20 11:21:30', 'admin');


-- 2017-2-23 by Hary --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('14310', '14300', '0', '0', 'function', 'Add Therapist Block', NULL, 'book:toBlockTherapistAdd,blockTherapistAddConfirm,blockAdd', '1', '2017-02-23 11:47:00', 'admin', '2017-02-23 11:47:00', 'admin');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('14320', '14300', '0', '0', 'function', 'Edit Therapist Block', NULL, 'book:toBlockTherapistUpdate,blockTherapistUpdateConfirm,blockUpdate', '1', '2017-02-23 14:47:44', 'admin', '2017-02-23 14:47:44', 'admin');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('14330', '14300', '0', '0', 'function', 'Remove Therapist Block', NULL, 'book:removeBlock', '1', '2017-02-23 14:49:59', 'admin', '2017-02-23 14:49:59', 'admin');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('14210', '14200', '0', '0', 'function', 'Add Room Block', '', 'book:toBlockRoomAdd,blockRoomAddConfirm,blockAdd', '1', '2017-02-23 11:47:00', 'admin', '2017-02-23 11:47:00', 'admin');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('14220', '14200', '0', '0', 'function', 'Edit Room Block', NULL, 'book:toBlockRoomUpdate,blockRoomUpdateConfirm,blockUpdate', '1', '2017-02-23 14:51:56', 'admin', '2017-02-23 14:51:56', 'admin');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('14230', '14200', '0', '0', 'function', 'Remove Room Block', NULL, 'book:removeBlock', '1', '2017-02-23 14:53:28', 'admin', '2017-02-23 14:53:28', 'admin');

-- 2017-2-24 by Hary --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('12400', '12100', '0', '0', 'function', 'Schedule', NULL, 'staff:toSchedule,schedule,toScheduleWeek', '1', '2017-02-24 14:39:05', 'admin', '2017-02-24 14:39:05', 'admin');


-- 2017-3-1 by Hary --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES ('11500', '11000', '0', '0', 'function', 'Member Advance List', NULL, 'member:toAdvanceView,advanceList', '1', '2017-03-01 17:39:06', 'admin', '2017-03-01 17:39:06', 'admin');

-- 2017-4-19 by Ivy --
UPDATE  sys_resource SET name='Remove',permission ='room:remove' where id=10130;

UPDATE  sys_resource SET name='Remove',permission ='shop:remove' where id=10230;

UPDATE  sys_resource SET name='Member Group Remove',permission ='member:groupRemove' where id=11230;

UPDATE  sys_resource SET name='Remove',permission ='consentForm:remove' where id=11430;

UPDATE  sys_resource SET name='Remove',permission ='staff:remove' where id=12130;

UPDATE  sys_resource SET name='Staff Group Remove',permission ='staff:groupRemove' where id=12330;

UPDATE  sys_resource SET name='Remove',permission ='category:remove' where id=13130;

UPDATE  sys_resource SET name='Remove',permission ='product:remove' where id=13240;

UPDATE  sys_resource SET name='Remove',permission ='supplier:remove' where id=13330;

UPDATE  sys_resource SET name='Remove',permission ='brand:remove' where id=13530;

UPDATE  sys_resource SET name='Delete',permission ='sales:delete' where id=15140;

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('10140', '10100', '0', '35', 'function', 'Enabled', NULL, 'room:enabled');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('11330', '11300', '0', '0', 'function', 'Remove', NULL, 'communicationChannel:remove');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('11140', '11100', '0', '0', 'function', 'Single View Of Client', NULL, 'member:singleViewOfClient');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('11150', '11100', '0', '0', 'function', 'Family View', NULL, 'member:toFamilyView');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('11160', '11100', '0', '0', 'function', 'Remove Member', NULL, 'member:remove');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('11170', '11100', '0', '0', 'function', 'Enabled Member', NULL, 'member:enable');
	
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('12150', '12100', '0', '0', 'function', 'Enabled Staff', NULL, 'staff:enable');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('18330', '18300', '0', '0', 'function', 'Remove', NULL, 'loyalty:remove');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('18430', '18400', '0', '0', 'function', 'Remove', NULL, 'awardRedemption:remove');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('19240', '19200', '0', '0', 'function', 'Remove', NULL, 'discount:discountRuleRemove');

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) 
	VALUES ('25240', '25200', '0', '0', 'function', 'Remove', NULL, 'commission:commissionRuleRemove');


INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12160', '12100', 'function', 'change passoword', 'staff:toChangePassword');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('18340', '18300', 'function', 'Adjust LL', 'loyalty:toAdjustLoyaltyLevel,adjustLoyaltyLevel');

-- 2017-06-24 by Ivy report settings to system settings module -
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28000', '1', 'module', 'Report', 'reportModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28100', '28000', 'function', 'Sales Details', 'report:toSalesDetails,listSalesDetails');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28200', '28000', 'function', 'Custom Report', 'report:toCustomer,printCustomer');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28300', '28000', 'function', 'Prepaid Outstanding', 'report:toPrepaidOutstanding,listPrepaidOutstanding');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28400', '28000', 'function', 'Complete Report', 'report:toComplete,printComplete');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28500', '28000', 'function', 'Capelli Revenue', 'report:toCapelliRevenue,listCapelliRevenue');


-- 2017-07-6 by Ivy service settings fro staff --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('12500', '12100', 'function', 'Service Settings', 'staff:toServiceSettings,saveServiceSettings');

-- 2017-07-20 by Ivy export --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('15150', '15100', 'function', 'Sales Export', 'sales:export');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28110', '28100', 'function', 'Export', 'report:salesDetailsExport');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28210', '28200', 'function', 'Generate PDF', 'report:printCustomer');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28600', '28000', 'function', 'Sales Details Report For Staff', 'report:toSalesDetailsForStaff,listSalesDetailsForStaff');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29000', '1', 'module', 'Salesforce', 'salesforceModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29100', '29000', 'function', 'Live', 'salesforceData:memberDetailsImport');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29110', '29100', 'function', 'Reset Data', 'salesforceData:resetDB');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29120', '29100', 'function', 'Import', 'salesforceData:importDataV2');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29130', '29100', 'function', 'Doownload Log', 'salesforceData:downloadLog');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29200', '29000', 'function', 'Demo', 'sfDataDEMO:memberDetailsImport');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29210', '29200', 'function', 'Reset Data', 'sfDataDEMO:resetDB');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29220', '29200', 'function', 'Import', 'sfDataDEMO:importDataV2');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('29230', '29200', 'function', 'Doownload Log', 'sfDataDEMO:downloadLog');

update `loccitane`.`sys_resource` set permission ='sfDataDEMO:memberDetailsImport,memberDetailsDataList' where id = 29200;

INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('30000', '1', 'module', 'test', 'testModule');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('30100', '30000', 'function', 'test', 'test:patchMemberLastUpdatedDate');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28610', '28600', 'function', 'Export', 'report:salesDetailsForStaffExport');

-- 2018-01-20 by Ivy  --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28700', '28000', 'function', 'Daily Report For Shop Manager', 'report:toDailyReportForShopManager,listDailyReportForShopManager');
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `type`, `name`, `permission`) VALUES ('28800', '28000', 'function', 'Daily Report For SOT Manager', 'report:toDailyReportForSOTManager,listDailyReportForSOTManager');

-- 2018-01-25 by Ivy  --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('15200', '15000', '0', '23', 'function', 'Survey List', NULL, 'survey:toView,list,send,notSend,entryEmail');

-- 2018-02-01 by jason  --
INSERT INTO loccitane.sys_resource (id,parent_id, module_order, display_order, type, name, description, permission, is_active, created, created_by, last_updated, last_updated_by) VALUES (15300,15000, 0, 0, 'function', 'evaluation', null, 'review:evaluation,listing,toView,listingToView', 1, '2018-02-01 11:36:22', 'admin', '2018-02-01 11:36:22', 'admin');
INSERT INTO loccitane.sys_resource (id,parent_id, module_order, display_order, type, name, description, permission, is_active, created, created_by, last_updated, last_updated_by) VALUES (15320,15300, 0, 0, 'function', 'reviewRatingShopList', null, 'review:reviewRatingShopList', 1, '2018-02-01 11:37:42', 'admin', '2018-02-01 11:37:42', 'admin');
INSERT INTO loccitane.sys_resource (id,parent_id, module_order, display_order, type, name, description, permission, is_active, created, created_by, last_updated, last_updated_by) VALUES (15330,15300, 0, 0, 'function', 'reviewRatingShopDetails', null, 'review:reviewRatingShopDetails', 1, '2018-02-01 11:38:26', 'admin', '2018-02-01 11:38:26', 'admin');
INSERT INTO loccitane.sys_resource (id,parent_id, module_order, display_order, type, name, description, permission, is_active, created, created_by, last_updated, last_updated_by) VALUES (15340,15300, 0, 0, 'function', 'ratingTreatmentList', null, 'review:ratingTreatmentList', 1, '2018-02-01 11:38:45', 'admin', '2018-02-01 11:38:45', 'admin');
INSERT INTO loccitane.sys_resource (id,parent_id, module_order, display_order, type, name, description, permission, is_active, created, created_by, last_updated, last_updated_by) VALUES (15350,15300, 0, 0, 'function', 'ratingTreatmentDetails', null, 'review:ratingTreatmentDetails', 1, '2018-02-01 11:39:14', 'admin', '2018-02-01 11:39:14', 'admin');
INSERT INTO loccitane.sys_resource (id,parent_id, module_order, display_order, type, name, description, permission, is_active, created, created_by, last_updated, last_updated_by) VALUES (15360,15300, 0, 0, 'function', 'ratingTreatmentTherapistList', null, 'review:ratingTreatmentTherapistList', 1, '2018-02-01 11:39:55', 'admin', '2018-02-01 11:39:55', 'admin');
INSERT INTO loccitane.sys_resource (id,parent_id, module_order, display_order, type, name, description, permission, is_active, created, created_by, last_updated, last_updated_by) VALUES (15370,15300, 0, 0, 'function', 'ratingTreatmentTherapistDetails', null, 'review:ratingTreatmentTherapistDetails', 1, '2018-02-01 11:40:20', 'admin', '2018-02-01 11:40:20', 'admin');

-- 2018-4-8 by jason --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('13250', '13200', '0', '141', 'function', 'To Product Select Shop', NULL, 'po:toProductSelectShop,productSelectShopSave');
-- deployed on production on 2018-04-11 by ivy --

-- 2018-4-23 by Ivy --
INSERT INTO `loccitane`.`sys_resource` (`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`) VALUES ('16131', '16100', '0', '133', 'function', 'Edit Expiry Date', NULL, 'prepaid:toEditExpiryDate,editExpiryDate');
-- 2018-5-3 by jason --
INSERT INTO `loccitane`.`sys_resource` VALUES ('10240', '10200', '0', '0', 'function', 'sort', null, 'shop:toSort,sort', '1', '2018-04-24 11:07:37', 'admin', '2018-04-24 11:07:41', 'admin');

-- deployed on production on 2018-05-4 by ivy --

INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('13700','13000','0','18','function','Bundle List',NULL,'bundle:toBundleView,bundleList');
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('13710','13700','0','181','function','Add',NULL,'bundle:bundleToAdd,bundleSave');
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('13720','13700','0','182','function','Edit',NULL,'bundle:bundleEdit');
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('13730','13700','0','183','function','Remove',NULL,'bundle:bundleRemove');

-- 2018-08-15 by Ivy --
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('14160','14100','0','183','function','Choose Bundle',NULL,'book:toChooseBundle,chooseBundle');

-- 2018-09-4 by rick --
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30102, 1, 12, 0, 'module', 'Import', NULL, 'importModule', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30103, 30102, 0, 0, 'function', 'Import', NULL, 'import:toView', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');

-- 2018-09-11 by rick --
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30104, 1, 13, 0, 'module', 'Attendance', NULL, 'attendanceModule', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30105, 30104, 0, 0, 'function', 'Clock In/Out', NULL, 'attendance:toAddClock', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30106, 30104, 0, 0, 'function', 'Attendance Record', NULL, 'attendance:attendanceRecord', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');

-- 2018-09-12 by rick --
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30107, 30106, 0, 0, 'function', 'Search', NULL, 'attendance:search', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30108, 30106, 0, 0, 'function', 'Remove', NULL, 'attendance:remove', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30109, 30106, 0, 0, 'function', 'Export', NULL, 'attendance:export', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`) VALUES (30110, 30106, 0, 0, 'function', 'Edit', NULL, 'attendance:toEdit,edit', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');

-- 2018-09-15 by ivy --
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('14400','14000','0','18','function','Book Batch',NULL,'bookBatch');
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('14410','14400','0','181','function','Add',NULL,'bookBatch:toAddBookBatch,confirmBookBatch,addBookBatch');
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('14420','14400','0','182','function','Search',NULL,'bookBatch:toListBookBatch,listBookBatch');
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('14421','14420','0','183','function','Edit',NULL,'bookBatch:toEditBookBatch,confirmBookBatch,editBookBatch');
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`,`module_order`,`display_order`,`type`,`name`,`description`,`permission`)VALUES('14422','14400','0','183','function','Remove',NULL,'bookBatch:removeBookBatch');

/* create by william -- 2018-10-11 */
INSERT INTO `loccitane`.`sys_resource` (`id`,`parent_id`, `type`, `name`, `permission`) VALUES ('30101', '11000', 'function', 'Member Export', 'member:export');

/* 2018-11-12 by rick */
INSERT INTO `loccitane`.`sys_resource`(`id`, `parent_id`, `module_order`, `display_order`, `type`, `name`, `description`, `permission`, `is_active`, `created`, `created_by`, `last_updated`, `last_updated_by`)
VALUES (30111, 16000, 0, 0, 'function', 'GC500 Gift Certificates', NULL, 'prepaid:toImportPrepaidGif', 1, '2018-9-4 17:29:53', 'admin', '2018-9-4 17:29:53', 'admin');

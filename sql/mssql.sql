/*
 Navicat Premium Data Transfer

 Source Server         : localsqlserver
 Source Server Type    : SQL Server
 Source Server Version : 15002000
 Source Host           : localhost:1433
 Source Catalog        : HistoryData
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 15002000
 File Encoding         : 65001

 Date: 07/12/2020 16:22:24
*/


-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_admin]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_admin]
GO

CREATE TABLE [dbo].[ums_admin] (
  [id] bigint  NULL,
  [username] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [password] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [icon] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [email] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [nick_name] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [note] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [create_time] datetime2(7)  NULL,
  [login_time] datetime2(7)  NULL,
  [status] int  NULL
)
GO

ALTER TABLE [dbo].[ums_admin] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_admin
-- ----------------------------
INSERT INTO [dbo].[ums_admin] ([id], [username], [password], [icon], [email], [nick_name], [note], [create_time], [login_time], [status]) VALUES (N'1', N'test', N'$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', N'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', N'test@qq.com', N'测试账号', NULL, N'2018-09-29 00:00:00.0000000', N'2018-09-29 00:00:00.0000000', N'1')
GO

INSERT INTO [dbo].[ums_admin] ([id], [username], [password], [icon], [email], [nick_name], [note], [create_time], [login_time], [status]) VALUES (N'3', N'admin', N'$2a$10$.E1FokumK5GIXWgKlg.Hc.i/0/2.qdAwYFL1zc5QHdyzpXOr38RZO', N'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', N'admin@163.com', N'系统管理员', N'系统管理员', N'2018-10-08 00:00:00.0000000', N'2019-04-20 00:00:00.0000000', N'1')
GO

INSERT INTO [dbo].[ums_admin] ([id], [username], [password], [icon], [email], [nick_name], [note], [create_time], [login_time], [status]) VALUES (N'4', N'macro', N'$2a$10$Bx4jZPR7GhEpIQfefDQtVeS58GfT5n6mxs/b4nLLK65eMFa16topa', N'string', N'macro@qq.com', N'macro', N'macro专用', N'2019-10-06 00:00:00.0000000', N'2020-02-03 00:00:00.0000000', N'1')
GO

INSERT INTO [dbo].[ums_admin] ([id], [username], [password], [icon], [email], [nick_name], [note], [create_time], [login_time], [status]) VALUES (N'6', N'productAdmin', N'$2a$10$6/.J.p.6Bhn7ic4GfoB5D.pGd7xSiD1a9M6ht6yO0fxzlKJPjRAGm', NULL, N'product@qq.com', N'商品管理员', N'只有商品权限', N'2020-02-07 00:00:00.0000000', NULL, N'1')
GO

INSERT INTO [dbo].[ums_admin] ([id], [username], [password], [icon], [email], [nick_name], [note], [create_time], [login_time], [status]) VALUES (N'7', N'orderAdmin', N'$2a$10$UqEhA9UZXjHHA3B.L9wNG.6aerrBjC6WHTtbv1FdvYPUI.7lkL6E.', NULL, N'order@qq.com', N'订单管理员', N'只有订单管理权限', N'2020-02-07 00:00:00.0000000', NULL, N'1')
GO

INSERT INTO [dbo].[ums_admin] ([id], [username], [password], [icon], [email], [nick_name], [note], [create_time], [login_time], [status]) VALUES (N'10', N'ceshi', N'$2a$10$RaaNo9CC0RSms8mc/gJpCuOWndDT4pHH0u5XgZdAAYFs1Uq4sOPRi', NULL, N'ceshi@qq.com', N'ceshi', NULL, N'2020-03-13 00:00:00.0000000', NULL, N'1')
GO


-- ----------------------------
-- Table structure for ums_admin_login_log
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_admin_login_log]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_admin_login_log]
GO

CREATE TABLE [dbo].[ums_admin_login_log] (
  [id] bigint  NULL,
  [admin_id] bigint  NULL,
  [create_time] datetime2(7)  NULL,
  [ip] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [address] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [user_agent] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ums_admin_login_log] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_admin_login_log
-- ----------------------------
INSERT INTO [dbo].[ums_admin_login_log] ([id], [admin_id], [create_time], [ip], [address], [user_agent]) VALUES (N'285', N'3', N'2020-08-24 00:00:00.0000000', N'0:0:0:0:0:0:0:1', NULL, NULL)
GO

INSERT INTO [dbo].[ums_admin_login_log] ([id], [admin_id], [create_time], [ip], [address], [user_agent]) VALUES (N'286', N'10', N'2020-08-24 00:00:00.0000000', N'0:0:0:0:0:0:0:1', NULL, NULL)
GO

INSERT INTO [dbo].[ums_admin_login_log] ([id], [admin_id], [create_time], [ip], [address], [user_agent]) VALUES (N'287', N'3', N'2020-12-01 00:00:00.0000000', N'0:0:0:0:0:0:0:1', NULL, NULL)
GO

INSERT INTO [dbo].[ums_admin_login_log] ([id], [admin_id], [create_time], [ip], [address], [user_agent]) VALUES (NULL, N'3', N'2020-12-07 14:25:25.5910000', N'0:0:0:0:0:0:0:1', NULL, NULL)
GO


-- ----------------------------
-- Table structure for ums_admin_role_relation
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_admin_role_relation]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_admin_role_relation]
GO

CREATE TABLE [dbo].[ums_admin_role_relation] (
  [id] bigint  NULL,
  [admin_id] bigint  NULL,
  [role_id] bigint  NULL
)
GO

ALTER TABLE [dbo].[ums_admin_role_relation] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_admin_role_relation
-- ----------------------------
INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'26', N'3', N'5')
GO

INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'27', N'6', N'1')
GO

INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'28', N'7', N'2')
GO

INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'29', N'1', N'5')
GO

INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'30', N'4', N'5')
GO

INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'31', N'8', N'5')
GO

INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'34', N'12', N'6')
GO

INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'38', N'13', N'5')
GO

INSERT INTO [dbo].[ums_admin_role_relation] ([id], [admin_id], [role_id]) VALUES (N'39', N'10', N'8')
GO


-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_menu]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_menu]
GO

CREATE TABLE [dbo].[ums_menu] (
  [id] bigint  NULL,
  [parent_id] bigint  NULL,
  [create_time] datetime2(7)  NULL,
  [title] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [level] int  NULL,
  [sort] int  NULL,
  [name] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [icon] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [hidden] int  NULL
)
GO

ALTER TABLE [dbo].[ums_menu] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'1', N'0', N'2020-02-02 00:00:00.0000000', N'商品', NULL, NULL, N'pms', N'product', N'1')
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'2', N'1', N'2020-02-02 00:00:00.0000000', N'商品列表', N'1', NULL, N'product', N'product-list', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'3', N'1', N'2020-02-02 00:00:00.0000000', N'添加商品', N'1', NULL, N'addProduct', N'product-add', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'4', N'1', N'2020-02-02 00:00:00.0000000', N'商品分类', N'1', NULL, N'productCate', N'product-cate', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'5', N'1', N'2020-02-02 00:00:00.0000000', N'商品类型', N'1', NULL, N'productAttr', N'product-attr', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'6', N'1', N'2020-02-02 00:00:00.0000000', N'品牌管理', N'1', NULL, N'brand', N'product-brand', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'7', N'0', N'2020-02-02 00:00:00.0000000', N'订单', NULL, NULL, N'oms', N'order', N'1')
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'8', N'7', N'2020-02-02 00:00:00.0000000', N'订单列表', N'1', NULL, N'order', N'product-list', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'9', N'7', N'2020-02-02 00:00:00.0000000', N'订单设置', N'1', NULL, N'orderSetting', N'order-setting', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'10', N'7', N'2020-02-02 00:00:00.0000000', N'退货申请处理', N'1', NULL, N'returnApply', N'order-return', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'11', N'7', N'2020-02-02 00:00:00.0000000', N'退货原因设置', N'1', NULL, N'returnReason', N'order-return-reason', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'12', N'0', N'2020-02-04 00:00:00.0000000', N'营销', NULL, NULL, N'sms', N'sms', N'1')
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'13', N'12', N'2020-02-04 00:00:00.0000000', N'秒杀活动列表', N'1', NULL, N'flash', N'sms-flash', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'14', N'12', N'2020-02-04 00:00:00.0000000', N'优惠券列表', N'1', NULL, N'coupon', N'sms-coupon', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'16', N'12', N'2020-02-07 00:00:00.0000000', N'品牌推荐', N'1', NULL, N'homeBrand', N'product-brand', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'17', N'12', N'2020-02-07 00:00:00.0000000', N'新品推荐', N'1', NULL, N'homeNew', N'sms-new', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'18', N'12', N'2020-02-07 00:00:00.0000000', N'人气推荐', N'1', NULL, N'homeHot', N'sms-hot', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'19', N'12', N'2020-02-07 00:00:00.0000000', N'专题推荐', N'1', NULL, N'homeSubject', N'sms-subject', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'20', N'12', N'2020-02-07 00:00:00.0000000', N'广告列表', N'1', NULL, N'homeAdvertise', N'sms-ad', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'21', N'0', N'2020-02-07 00:00:00.0000000', N'权限', NULL, NULL, N'ums', N'ums', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'22', N'21', N'2020-02-07 00:00:00.0000000', N'用户列表', N'1', NULL, N'admin', N'ums-admin', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'23', N'21', N'2020-02-07 00:00:00.0000000', N'角色列表', N'1', NULL, N'role', N'ums-role', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'24', N'21', N'2020-02-07 00:00:00.0000000', N'菜单列表', N'1', NULL, N'menu', N'ums-menu', NULL)
GO

INSERT INTO [dbo].[ums_menu] ([id], [parent_id], [create_time], [title], [level], [sort], [name], [icon], [hidden]) VALUES (N'25', N'21', N'2020-02-07 00:00:00.0000000', N'资源列表', N'1', NULL, N'resource', N'ums-resource', NULL)
GO


-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_resource]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_resource]
GO

CREATE TABLE [dbo].[ums_resource] (
  [id] bigint  NULL,
  [create_time] datetime2(7)  NULL,
  [name] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [url] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [description] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [category_id] bigint  NULL
)
GO

ALTER TABLE [dbo].[ums_resource] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_resource
-- ----------------------------
INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'1', N'2020-02-04 00:00:00.0000000', N'商品品牌管理', N'/brand/**', NULL, N'1')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'2', N'2020-02-04 00:00:00.0000000', N'商品属性分类管理', N'/productAttribute/**', NULL, N'1')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'3', N'2020-02-04 00:00:00.0000000', N'商品属性管理', N'/productAttribute/**', NULL, N'1')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'4', N'2020-02-04 00:00:00.0000000', N'商品分类管理', N'/productCategory/**', NULL, N'1')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'5', N'2020-02-04 00:00:00.0000000', N'商品管理', N'/product/**', NULL, N'1')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'6', N'2020-02-04 00:00:00.0000000', N'商品库存管理', N'/sku/**', NULL, N'1')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'8', N'2020-02-05 00:00:00.0000000', N'订单管理', N'/order/**', NULL, N'2')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'9', N'2020-02-05 00:00:00.0000000', N'订单退货申请管理', N'/returnApply/**', NULL, N'2')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'10', N'2020-02-05 00:00:00.0000000', N'退货原因管理', N'/returnReason/**', NULL, N'2')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'11', N'2020-02-05 00:00:00.0000000', N'订单设置管理', N'/orderSetting/**', NULL, N'2')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'12', N'2020-02-05 00:00:00.0000000', N'收货地址管理', N'/companyAddress/**', NULL, N'2')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'13', N'2020-02-07 00:00:00.0000000', N'优惠券管理', N'/coupon/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'14', N'2020-02-07 00:00:00.0000000', N'优惠券领取记录管理', N'/couponHistory/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'15', N'2020-02-07 00:00:00.0000000', N'限时购活动管理', N'/flash/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'16', N'2020-02-07 00:00:00.0000000', N'限时购商品关系管理', N'/flashProductRelation/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'17', N'2020-02-07 00:00:00.0000000', N'限时购场次管理', N'/flashSession/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'18', N'2020-02-07 00:00:00.0000000', N'首页轮播广告管理', N'/home/advertise/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'19', N'2020-02-07 00:00:00.0000000', N'首页品牌管理', N'/home/brand/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'20', N'2020-02-07 00:00:00.0000000', N'首页新品管理', N'/home/newProduct/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'21', N'2020-02-07 00:00:00.0000000', N'首页人气推荐管理', N'/home/recommendProduct/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'22', N'2020-02-07 00:00:00.0000000', N'首页专题推荐管理', N'/home/recommendSubject/**', NULL, N'3')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'23', N'2020-02-07 00:00:00.0000000', N'商品优选管理', N'/prefrenceArea/**', NULL, N'5')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'24', N'2020-02-07 00:00:00.0000000', N'商品专题管理', N'/subject/**', NULL, N'5')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'25', N'2020-02-07 00:00:00.0000000', N'后台用户管理', N'/admin/**', NULL, N'4')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'26', N'2020-02-07 00:00:00.0000000', N'后台用户角色管理', N'/role/**', NULL, N'4')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'27', N'2020-02-07 00:00:00.0000000', N'后台菜单管理', N'/menu/**', NULL, N'4')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'28', N'2020-02-07 00:00:00.0000000', N'后台资源分类管理', N'/resourceCategory/**', NULL, N'4')
GO

INSERT INTO [dbo].[ums_resource] ([id], [create_time], [name], [url], [description], [category_id]) VALUES (N'29', N'2020-02-07 00:00:00.0000000', N'后台资源管理', N'/resource/**', NULL, N'4')
GO


-- ----------------------------
-- Table structure for ums_resource_category
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_resource_category]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_resource_category]
GO

CREATE TABLE [dbo].[ums_resource_category] (
  [id] bigint  NULL,
  [create_time] datetime2(7)  NULL,
  [name] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [sort] int  NULL
)
GO

ALTER TABLE [dbo].[ums_resource_category] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_resource_category
-- ----------------------------
INSERT INTO [dbo].[ums_resource_category] ([id], [create_time], [name], [sort]) VALUES (N'1', N'2020-02-05 00:00:00.0000000', N'商品模块', NULL)
GO

INSERT INTO [dbo].[ums_resource_category] ([id], [create_time], [name], [sort]) VALUES (N'2', N'2020-02-05 00:00:00.0000000', N'订单模块', NULL)
GO

INSERT INTO [dbo].[ums_resource_category] ([id], [create_time], [name], [sort]) VALUES (N'3', N'2020-02-05 00:00:00.0000000', N'营销模块', NULL)
GO

INSERT INTO [dbo].[ums_resource_category] ([id], [create_time], [name], [sort]) VALUES (N'4', N'2020-02-05 00:00:00.0000000', N'权限模块', NULL)
GO

INSERT INTO [dbo].[ums_resource_category] ([id], [create_time], [name], [sort]) VALUES (N'5', N'2020-02-07 00:00:00.0000000', N'内容模块', NULL)
GO

INSERT INTO [dbo].[ums_resource_category] ([id], [create_time], [name], [sort]) VALUES (N'6', N'2020-02-07 00:00:00.0000000', N'其他模块', NULL)
GO


-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_role]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_role]
GO

CREATE TABLE [dbo].[ums_role] (
  [id] bigint  NULL,
  [name] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [description] nvarchar(254) COLLATE Chinese_PRC_CI_AS  NULL,
  [admin_count] int  NULL,
  [create_time] datetime2(7)  NULL,
  [status] int  NULL,
  [sort] int  NULL
)
GO

ALTER TABLE [dbo].[ums_role] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO [dbo].[ums_role] ([id], [name], [description], [admin_count], [create_time], [status], [sort]) VALUES (N'1', N'商品管理员', N'只能查看及操作商品', NULL, N'2020-02-03 00:00:00.0000000', N'1', NULL)
GO

INSERT INTO [dbo].[ums_role] ([id], [name], [description], [admin_count], [create_time], [status], [sort]) VALUES (N'2', N'订单管理员', N'只能查看及操作订单', NULL, N'2018-09-30 00:00:00.0000000', N'1', NULL)
GO

INSERT INTO [dbo].[ums_role] ([id], [name], [description], [admin_count], [create_time], [status], [sort]) VALUES (N'5', N'超级管理员', N'拥有所有查看和操作功能', NULL, N'2020-02-02 00:00:00.0000000', N'1', NULL)
GO

INSERT INTO [dbo].[ums_role] ([id], [name], [description], [admin_count], [create_time], [status], [sort]) VALUES (N'8', N'权限管理员', N'用于权限模块所有操作功能', NULL, N'2020-08-24 00:00:00.0000000', N'1', NULL)
GO


-- ----------------------------
-- Table structure for ums_role_menu_relation
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_role_menu_relation]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_role_menu_relation]
GO

CREATE TABLE [dbo].[ums_role_menu_relation] (
  [id] bigint  NULL,
  [role_id] bigint  NULL,
  [menu_id] bigint  NULL
)
GO

ALTER TABLE [dbo].[ums_role_menu_relation] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'33', N'1', N'1')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'34', N'1', N'2')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'35', N'1', N'3')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'36', N'1', N'4')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'37', N'1', N'5')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'38', N'1', N'6')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'53', N'2', N'7')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'54', N'2', N'8')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'55', N'2', N'9')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'56', N'2', N'10')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'57', N'2', N'11')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'72', N'5', N'1')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'73', N'5', N'2')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'74', N'5', N'3')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'75', N'5', N'4')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'76', N'5', N'5')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'77', N'5', N'6')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'78', N'5', N'7')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'79', N'5', N'8')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'80', N'5', N'9')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'81', N'5', N'10')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'82', N'5', N'11')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'83', N'5', N'12')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'84', N'5', N'13')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'85', N'5', N'14')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'86', N'5', N'16')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'87', N'5', N'17')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'88', N'5', N'18')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'89', N'5', N'19')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'90', N'5', N'20')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'91', N'5', N'21')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'92', N'5', N'22')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'93', N'5', N'23')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'94', N'5', N'24')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'95', N'5', N'25')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'96', N'6', N'21')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'97', N'6', N'22')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'98', N'6', N'23')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'99', N'6', N'24')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'100', N'6', N'25')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'101', N'7', N'21')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'102', N'7', N'22')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'103', N'7', N'23')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'104', N'7', N'24')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'105', N'7', N'25')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'106', N'8', N'21')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'107', N'8', N'22')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'108', N'8', N'23')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'109', N'8', N'24')
GO

INSERT INTO [dbo].[ums_role_menu_relation] ([id], [role_id], [menu_id]) VALUES (N'110', N'8', N'25')
GO


-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ums_role_resource_relation]') AND type IN ('U'))
	DROP TABLE [dbo].[ums_role_resource_relation]
GO

CREATE TABLE [dbo].[ums_role_resource_relation] (
  [id] bigint  NULL,
  [role_id] bigint  NULL,
  [resource_id] bigint  NULL
)
GO

ALTER TABLE [dbo].[ums_role_resource_relation] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'103', N'2', N'8')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'104', N'2', N'9')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'105', N'2', N'10')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'106', N'2', N'11')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'107', N'2', N'12')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'142', N'5', N'1')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'143', N'5', N'2')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'144', N'5', N'3')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'145', N'5', N'4')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'146', N'5', N'5')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'147', N'5', N'6')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'148', N'5', N'8')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'149', N'5', N'9')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'150', N'5', N'10')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'151', N'5', N'11')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'152', N'5', N'12')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'153', N'5', N'13')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'154', N'5', N'14')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'155', N'5', N'15')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'156', N'5', N'16')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'157', N'5', N'17')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'158', N'5', N'18')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'159', N'5', N'19')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'160', N'5', N'20')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'161', N'5', N'21')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'162', N'5', N'22')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'163', N'5', N'23')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'164', N'5', N'24')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'165', N'5', N'25')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'166', N'5', N'26')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'167', N'5', N'27')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'168', N'5', N'28')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'169', N'5', N'29')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'170', N'1', N'1')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'171', N'1', N'2')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'172', N'1', N'3')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'173', N'1', N'4')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'174', N'1', N'5')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'175', N'1', N'6')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'176', N'1', N'23')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'177', N'1', N'24')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'178', N'6', N'25')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'179', N'6', N'26')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'180', N'6', N'27')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'181', N'6', N'28')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'182', N'6', N'29')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'205', N'7', N'25')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'206', N'7', N'26')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'207', N'7', N'27')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'208', N'7', N'28')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'209', N'7', N'29')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'210', N'7', N'31')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'211', N'8', N'25')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'212', N'8', N'26')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'213', N'8', N'27')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'214', N'8', N'28')
GO

INSERT INTO [dbo].[ums_role_resource_relation] ([id], [role_id], [resource_id]) VALUES (N'215', N'8', N'29')
GO


-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[web_log]') AND type IN ('U'))
	DROP TABLE [dbo].[web_log]
GO

CREATE TABLE [dbo].[web_log] (
  [id] nvarchar(32) NOT NULL,
  [description] nvarchar(255) NULL,
  [username] nvarchar(255) NULL,
  [start_time] datetime2(7) NULL,
  [spend_time] int NULL,
  [base_path] nvarchar(255) NULL,
  [uri] nvarchar(255) NULL,
  [url] nvarchar(255) NULL,
  [method] nvarchar(255) NULL,
  [ip] nvarchar(255) NULL,
  [parameter] text NULL,
  [result] text NULL
)
GO

ALTER TABLE [dbo].[web_log] SET (LOCK_ESCALATION = TABLE)
GO


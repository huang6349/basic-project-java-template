-- noinspection SqlNoDataSourceInspectionForFile
-- noinspection SqlDialectInspectionForFile

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_tenant
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_tenant`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`             varchar(256)                       NOT NULL COMMENT '租户名称',
    `avatar`           varchar(256)                       NULL COMMENT '租户头像',
    `category`         tinyint                            NULL COMMENT '租户类别（枚举字典）',
    `address`          varchar(256)                       NULL COMMENT '租户地址',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `status`           tinyint  DEFAULT 0                 NOT NULL COMMENT '租户状态(0-启用, 1-禁用)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '租户信息';

-- ----------------------------
-- Table structure for tb_tenant_assoc
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_tenant_assoc`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_id`        bigint                             NOT NULL COMMENT '租户主键(所属租户)',
    `assoc`            varchar(256)                       NOT NULL COMMENT '关联表名',
    `assoc_id`         bigint                             NOT NULL COMMENT '关联主键',
    `effective`        tinyint  DEFAULT 0                 NOT NULL COMMENT '限制时间(0-不限制, 1-限制)',
    `effective_time`   datetime                           NULL COMMENT '有效时间',
    `category`         tinyint  DEFAULT 0                 NULL COMMENT '关联类别(枚举字典)',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '租户关联';

-- ----------------------------
-- Table structure for tb_tenant_property
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_tenant_property`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_id`        bigint                             NOT NULL COMMENT '租户主键(所属租户)',
    `group`            varchar(256)                       NOT NULL COMMENT '属性分组',
    `name`             varchar(256)                       NOT NULL COMMENT '属性名称',
    `data`             varchar(256)                       NULL COMMENT '属性数据',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '租户属性';

-- ----------------------------
-- Table structure for tb_perm
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_perm`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`             varchar(256)                       NOT NULL COMMENT '权限名称',
    `code`             varchar(256)                       NOT NULL COMMENT '权限代码',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `status`           tinyint  DEFAULT 0                 NOT NULL COMMENT '权限状态(0-启用, 1-禁用)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '权限信息';

-- ----------------------------
-- Records of tb_perm
-- ----------------------------
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000000, '上帝权限', '*');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000101, '租户管理', 'tenant:*');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000102, '租户新增', 'tenant:add');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000103, '租户修改', 'tenant:update');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000104, '租户删除', 'tenant:delete');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000105, '租户查询', 'tenant:query');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000201, '权限管理', 'perm:*');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000202, '权限新增', 'perm:add');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000203, '权限修改', 'perm:update');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000204, '权限删除', 'perm:delete');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000205, '权限查询', 'perm:query');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000301, '部门管理', 'dept:*');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000302, '部门新增', 'dept:add');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000303, '部门修改', 'dept:update');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000304, '部门删除', 'dept:delete');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000305, '部门查询', 'dept:query');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000401, '角色管理', 'role:*');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000402, '角色新增', 'role:add');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000403, '角色修改', 'role:update');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000404, '角色删除', 'role:delete');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000405, '角色查询', 'role:query');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000406, '分配权限', 'role:perm');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000501, '用户管理', 'user:*');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000502, '用户新增', 'user:add');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000503, '用户修改', 'user:update');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000504, '用户删除', 'user:delete');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000505, '用户查询', 'user:query');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000506, '分配租户', 'user:tenant');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000507, '分配部门', 'user:dept');
INSERT INTO project.`tb_perm` (`id`, `name`, `code`) VALUES (10000000000000508, '分配角色', 'user:role');

-- ----------------------------
-- Table structure for tb_perm_assoc
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_perm_assoc`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `perm_id`          bigint                             NOT NULL COMMENT '权限主键(所属权限)',
    `assoc`            varchar(256)                       NOT NULL COMMENT '关联表名',
    `assoc_id`         bigint                             NOT NULL COMMENT '关联主键',
    `effective`        tinyint  DEFAULT 0                 NOT NULL COMMENT '限制时间(0-不限制, 1-限制)',
    `effective_time`   datetime                           NULL COMMENT '有效时间',
    `category`         tinyint  DEFAULT 0                 NULL COMMENT '关联类别(枚举字典)',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '权限关联（授权信息）';

-- ----------------------------
-- Records of tb_perm_assoc
-- ----------------------------
INSERT INTO project.`tb_perm_assoc` (`id`, `perm_id`, `assoc`, `assoc_id`) VALUES (10000000000000000, 10000000000000000, 'tb_account', 10000000000000000);
INSERT INTO project.`tb_perm_assoc` (`id`, `perm_id`, `assoc`, `assoc_id`) VALUES (10000000000000001, 10000000000000301, 'tb_role', 10000000000000000);

-- ----------------------------
-- Table structure for tb_dept
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_dept`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`        bigint   DEFAULT 0                 NOT NULL COMMENT '父级节点(父级主键)',
    `path`             varchar(512)                       NOT NULL COMMENT '节点路径',
    `sort`             tinyint  DEFAULT 0                 NOT NULL COMMENT '节点顺序',
    `name`             varchar(256)                       NOT NULL COMMENT '部门名称',
    `code`             varchar(256)                       NOT NULL COMMENT '部门代码',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `status`           tinyint  DEFAULT 0                 NOT NULL COMMENT '部门状态(0-启用, 1-禁用)',
    `tenant_id`        bigint                             NULL COMMENT '租户主键(所属租户)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '部门信息';

-- ----------------------------
-- Table structure for tb_dept_assoc
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_dept_assoc`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `dept_id`          bigint                             NOT NULL COMMENT '部门主键(所属部门)',
    `assoc`            varchar(256)                       NOT NULL COMMENT '关联表名',
    `assoc_id`         bigint                             NOT NULL COMMENT '关联主键',
    `effective`        tinyint  DEFAULT 0                 NOT NULL COMMENT '限制时间(0-不限制, 1-限制)',
    `effective_time`   datetime                           NULL COMMENT '有效时间',
    `category`         tinyint  DEFAULT 0                 NULL COMMENT '关联类别(枚举字典)',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `tenant_id`        bigint                             NULL COMMENT '租户主键(所属租户)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '部门关联';

-- ----------------------------
-- Table structure for tb_dept_property
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_dept_property`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `dept_id`          bigint                             NOT NULL COMMENT '部门主键(所属部门)',
    `group`            varchar(256)                       NOT NULL COMMENT '属性分组',
    `name`             varchar(256)                       NOT NULL COMMENT '属性名称',
    `data`             varchar(256)                       NULL COMMENT '属性数据',
    `tenant_id`        bigint                             NULL COMMENT '租户主键(所属租户)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '部门属性';

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_role`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`             varchar(256)                       NOT NULL COMMENT '角色名称',
    `code`             varchar(256)                       NOT NULL COMMENT '角色代码',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `status`           tinyint  DEFAULT 0                 NOT NULL COMMENT '角色状态(0-启用, 1-禁用)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '角色信息';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO project.`tb_role` (`id`, `name`, `code`) VALUES (10000000000000000, '系统管理员', 'admin');

-- ----------------------------
-- Table structure for tb_role_assoc
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_role_assoc`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`          bigint                             NOT NULL COMMENT '角色主键(所属角色)',
    `assoc`            varchar(256)                       NOT NULL COMMENT '关联表名',
    `assoc_id`         bigint                             NOT NULL COMMENT '关联主键',
    `effective`        tinyint  DEFAULT 0                 NOT NULL COMMENT '限制时间(0-不限制, 1-限制)',
    `effective_time`   datetime                           NULL COMMENT '有效时间',
    `category`         tinyint  DEFAULT 0                 NULL COMMENT '关联类别(枚举字典)',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `tenant_id`        bigint                             NULL COMMENT '租户主键(所属租户)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '角色关联';

-- ----------------------------
-- Records of tb_role_assoc
-- ----------------------------
INSERT INTO project.`tb_role_assoc` (`id`, `role_id`, `assoc`, `assoc_id`, `tenant_id`) VALUES (10000000000000000, 10000000000000000, 'tb_account', 10000000000000000, 10000000000000000);

-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_account`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`         varchar(256)                       NOT NULL COMMENT '用户帐号',
    `password`         varchar(256)                       NULL COMMENT '用户密码',
    `salt`             varchar(256)                       NULL COMMENT '盐',
    `mobile`           varchar(256)                       NULL COMMENT '手机号码',
    `email`            varchar(256)                       NULL COMMENT '用户邮箱',
    `login_time`       datetime                           NULL COMMENT '登录时间',
    `status`           tinyint  DEFAULT 0                 NOT NULL COMMENT '帐号状态(0-启用, 1-禁用)',
    `tenant_id`        bigint                             NULL COMMENT '租户主键(默认租户)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '帐号信息';

-- ----------------------------
-- Records of tb_account
-- ----------------------------
INSERT INTO project.`tb_account` (`id`, `username`, `password`, `salt`, `tenant_id`) VALUES (10000000000000000, 'admin', '$2a$10$QyjZ4c6BaPD3693XArMopey0RPoKkiIfqHAxaxapijuYbK9takS.a', '$2a$10$QyjZ4c6BaPD3693XArMope', 10000000000000000);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_user`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`       bigint                             NOT NULL COMMENT '帐号主键(所属帐号)',
    `realname`         varchar(256)                       NULL COMMENT '真实姓名',
    `nickname`         varchar(256)                       NULL COMMENT '用户昵称',
    `avatar`           varchar(256)                       NULL COMMENT '用户头像',
    `id_card`          varchar(256)                       NULL COMMENT '身份证号',
    `gender`           tinyint                            NULL COMMENT '用户性别（枚举字典）',
    `birthday`         datetime                           NULL COMMENT '用户生日',
    `address`          varchar(256)                       NULL COMMENT '用户地址',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `account_id` (`account_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '用户信息';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO project.`tb_user` (`id`, `account_id`, `gender`) VALUES (10000000000000000, 10000000000000000, 0);

-- ----------------------------
-- Table structure for tb_account_assoc
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_account_assoc`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`       bigint                             NOT NULL COMMENT '帐号主键(所属帐号)',
    `assoc`            varchar(256)                       NOT NULL COMMENT '关联表名',
    `assoc_id`         bigint                             NOT NULL COMMENT '关联主键',
    `effective`        tinyint  DEFAULT 0                 NOT NULL COMMENT '限制时间(0-不限制, 1-限制)',
    `effective_time`   datetime                           NULL COMMENT '有效时间',
    `category`         tinyint  DEFAULT 0                 NULL COMMENT '关联类别(枚举字典)',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '帐号关联';

-- ----------------------------
-- Table structure for tb_account_record
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_account_record`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`       bigint                             NOT NULL COMMENT '帐号主键(所属帐号)',
    `extras`           varchar(512)                       NULL COMMENT '额外信息',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '帐号记录';

-- ----------------------------
-- Table structure for tb_account_record_property
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_account_record_property`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `record_id`        bigint                             NOT NULL COMMENT '记录主键(所属记录)',
    `group`            varchar(256)                       NOT NULL COMMENT '属性分组',
    `name`             varchar(256)                       NOT NULL COMMENT '属性名称',
    `data`             varchar(256)                       NULL COMMENT '属性数据',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '记录属性（帐号记录）';

-- ----------------------------
-- Table structure for tb_file
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_file`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `url`              varchar(512)                       NOT NULL COMMENT '文件访问地址',
    `size`             bigint                             NULL COMMENT '文件大小，单位字节',
    `filename`         varchar(256)                       NULL COMMENT '文件名称',
    `orig_filename`    varchar(256)                       NULL COMMENT '原始文件名',
    `base_path`        varchar(256)                       NULL COMMENT '基础存储路径',
    `path`             varchar(256)                       NULL COMMENT '存储路径',
    `ext`              varchar(32)                        NULL COMMENT '文件扩展名',
    `content_type`     varchar(128)                       NULL COMMENT 'MIME类型',
    `platform`         varchar(32)                        NULL COMMENT '存储平台',
    `th_url`           varchar(512)                       NULL COMMENT '缩略图访问路径',
    `th_filename`      varchar(256)                       NULL COMMENT '缩略图名称',
    `th_size`          bigint                             NULL COMMENT '缩略图大小，单位字节',
    `th_content_type`  varchar(128)                       NULL COMMENT '缩略图MIME类型',
    `object_id`        varchar(32)                        NULL COMMENT '文件所属对象ID',
    `object_type`      varchar(32)                        NULL COMMENT '文件所属对象类型，例如用户头像，评价图片',
    `metadata`         text                               NULL COMMENT '文件元数据',
    `user_metadata`    text                               NULL COMMENT '文件用户元数据',
    `th_metadata`      text                               NULL COMMENT '缩略图元数据',
    `th_user_metadata` text                               NULL COMMENT '缩略图用户元数据',
    `attr`             text                               NULL COMMENT '附加属性',
    `file_acl`         varchar(32)                        NULL COMMENT '文件ACL',
    `th_file_acl`      varchar(32)                        NULL COMMENT '缩略图文件ACL',
    `hash_info`        text                               NULL COMMENT '哈希信息',
    `upload_id`        varchar(128)                       NULL COMMENT '上传ID，仅在手动分片上传时使用',
    `upload_status`    tinyint                            NULL COMMENT '上传状态，仅在手动分片上传时使用，1：初始化完成，2：上传完成',
    `status`           tinyint  DEFAULT 0                 NOT NULL COMMENT '文件状态(0-未使用, 1-已使用)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '文件信息';

-- ----------------------------
-- Table structure for tb_file_part
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_file_part`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform`         varchar(32)                        NULL COMMENT '存储平台',
    `e_tag`            varchar(256)                       NULL COMMENT '分片ETag',
    `part_number`      tinyint                            NULL COMMENT '分片号。每一个上传的分片都有一个分片号，一般情况下取值范围是1~10000',
    `part_size`        bigint                             NULL COMMENT '文件大小，单位字节',
    `hash_info`        text                               NULL COMMENT '哈希信息',
    `upload_id`        varchar(128)                       NULL COMMENT '上传ID，仅在手动分片上传时使用',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '文件分片';

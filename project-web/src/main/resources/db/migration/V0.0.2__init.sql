-- noinspection SqlNoDataSourceInspectionForFile
-- noinspection SqlDialectInspectionForFile

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_example
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_example`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`             varchar(256)                       NOT NULL COMMENT '示例名称',
    `code`             varchar(256)                       NOT NULL COMMENT '示例代码',
    `desc`             varchar(512)                       NULL COMMENT '备注',
    `status`           tinyint  DEFAULT 0                 NOT NULL COMMENT '示例状态(0-启用, 1-禁用)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `version`          bigint   DEFAULT 0                 NOT NULL COMMENT '更新版本',
    `is_deleted`       tinyint  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '示例信息';

-- ----------------------------
-- Table structure for tb_example_property
-- ----------------------------
CREATE TABLE IF NOT EXISTS project.`tb_example_property`
(
    `id`               bigint                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `example_id`       bigint                             NOT NULL COMMENT '示例主键(所属测试)',
    `group`            varchar(256)                       NOT NULL COMMENT '属性分组',
    `name`             varchar(256)                       NOT NULL COMMENT '属性名称',
    `data`             varchar(256)                       NULL COMMENT '属性数据',
    `tenant_id`        bigint                             NULL COMMENT '租户主键(所属租户)',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `update_time`      datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT '示例属性';

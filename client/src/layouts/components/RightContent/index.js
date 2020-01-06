import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Menu } from 'antd';
import Avatar from './Avatar';
import styles from './index.css';

export default function RightContentView({ username, avatarMenus, onSelect, onPoweroff }) {
  return (
    <Menu className={styles['layout']} mode="horizontal" selectedKeys={[]}>
      <Menu.Item className={styles['user']} key="avatar">
        <Avatar username={username}>
          <Avatar.AvatarMenu menus={avatarMenus} onSelect={onSelect} onPoweroff={onPoweroff} />
        </Avatar>
      </Menu.Item>
    </Menu>
  );
}

RightContentView.propTypes = {
  username: PropTypes.string,
  avatarMenus: PropTypes.array,
  onSelect: PropTypes.func,
  onPoweroff: PropTypes.func,
};

RightContentView.defaultProps = {};

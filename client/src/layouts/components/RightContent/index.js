import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Menu } from 'antd';

import Avatar from './Avatar';

export default function RightContentView({ theme, username, avatarMenus, onAvatarClick, onPoweroff }) {
  return (
    <Menu mode="horizontal" selectedKeys={[]} theme={theme}>
      <Menu.Item key="avatar">
        <Avatar username={username}>
          <Avatar.AvatarMenu menus={avatarMenus} onClick={onAvatarClick} onPoweroff={onPoweroff} />
        </Avatar>
      </Menu.Item>
    </Menu>
  );
}

RightContentView.propTypes = {
  theme: PropTypes.string,
  username: PropTypes.string,
  avatarMenus: PropTypes.array,
  onAvatarClick: PropTypes.func,
  onPoweroff: PropTypes.func,
};

RightContentView.defaultProps = {};

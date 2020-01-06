import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Popover, Avatar } from 'antd';
import isEmpty from 'lodash/isEmpty';
import AvatarMenu from './AvatarMenu';
import styles from './Avatar.css';

export default function AvatarView({ username, children }) {
  const content = React.Children.map(children, (child) => {
    if (child.type === AvatarMenu) {
      return React.cloneElement(child);
    }
    return null;
  });

  return (
    <Popover
      arrowPointAtCenter={!0}
      overlayClassName={styles['popover']}
      placement="bottomRight"
      trigger="click"
      content={isEmpty(content) ? <AvatarMenu /> : content}
      title={`当前用户：${username}`}
    >
      <div className={styles['layout']}>
        <Avatar icon="user" />
      </div>
    </Popover>
  );
}

AvatarView.propTypes = {
  username: PropTypes.string.isRequired,
};

AvatarView.defaultProps = {
  username: '无',
};

AvatarView.AvatarMenu = AvatarMenu;

import * as React from 'react';
import * as PropTypes from 'prop-types';
import { RouteContext } from '@ant-design/pro-layout';

export default function MenuHeaderView({ title }) {
  const { isMobile } = React.useContext(RouteContext);

  if (isMobile) {
    return <React.Fragment />;
  }

  return <div style={{ userSelect: 'none', paddingLeft: 24 }}>{title}</div>;
}

MenuHeaderView.propTypes = {
  title: PropTypes.string.isRequired,
};

MenuHeaderView.defaultProps = {};

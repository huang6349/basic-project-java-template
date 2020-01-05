import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Tag } from 'antd';
import { COLOR } from '@/constant';

export default function DataStateTagView({ state, text }) {
  let color = COLOR['green'];
  if (state === 0) color = COLOR['red'];
  if (state === 2) color = COLOR['gold'];
  return <Tag color={color}>{text}</Tag>;
}

DataStateTagView.propTypes = {
  state: PropTypes.number.isRequired,
  text: PropTypes.string.isRequired,
};

DataStateTagView.defaultProps = {};

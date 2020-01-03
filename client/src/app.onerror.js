import { message } from 'antd';

const onError = (err) => {
  err.preventDefault();
  message.error(err['message']);
};

export default onError;

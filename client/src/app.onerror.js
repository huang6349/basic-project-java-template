import { notification, message } from 'antd';

const onError = (err, dispatch) => {
  err.preventDefault();

  const { name, data = {} } = err;
  if (name !== 'ResponseError') return;
  if (data['state'] === 401) {
    dispatch({ type: 'global/logout' });
    notification.warning({
      description: data['message'],
      message: '登录验证提示',
    });
  } else {
    message.error(data['message']);
  }
};

export default onError;

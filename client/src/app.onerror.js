import { notification, message } from 'antd';

const onError = (error, dispatch) => {
  error.preventDefault();

  if (error['response']) {
    if (error['response']['status'] === 401) {
      notification.close();
      dispatch({ type: 'global/logout' }).then(() => {
        notification.warning({ description: error['data']['message'], message: '登录验证提示' });
      });
    } else {
      message.error(error['data']['message']);
    }
  }
};

export default onError;

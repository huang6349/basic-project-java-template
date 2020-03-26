import { message } from 'antd';
import NProgress from 'nprogress';
import localforage from 'localforage';
import { extend } from 'umi-request';
import { TOKEN } from '@/constant';
import 'nprogress/nprogress.css';

NProgress.configure({ showSpinner: false });

function errorHandler(error) {
  NProgress.done();
  throw error;
}

const request = extend({ credentials: 'include', errorHandler });

request.use(async (ctx, next) => {
  const { req } = ctx;
  const { url, options = {} } = req;
  if (url.indexOf('/api') !== 0) ctx.req.url = `/api${url}`;
  options.headers = options.headers || {};
  options.headers['Content-Type'] = 'application/json;charset=UTF-8';
  options.headers['Accept'] = 'application/json';
  const token = await localforage.getItem(TOKEN['name']);
  if (token) options.headers['Authorization'] = token;
  ctx.req.options = options;
  NProgress.start();
  NProgress.inc();
  await next();
  NProgress.done();
});

request.interceptors.response.use(async (response, options) => {
  if (response.headers.get('Authorization')) {
    await localforage.setItem(TOKEN['name'], response.headers.get('Authorization'));
  }
  if (response.headers.get('Content-Type') === 'application/octet-stream') {
    const fileName = decodeURI(response.headers.get('Content-Disposition').split('fileName=')[1]);
    const blob = new Blob([await response.clone().blob()]);
    if ('download' in document.createElement('a')) {
      // 非IE下载
      const elink = document.createElement('a');
      elink.download = fileName;
      elink.style.display = 'none';
      elink.href = URL.createObjectURL(blob);
      document.body.appendChild(elink);
      elink.click();
      URL.revokeObjectURL(elink.href); // 释放URL 对象
      document.body.removeChild(elink);
    } else {
      // IE10+下载
      navigator.msSaveBlob(blob, fileName);
    }
  } else {
    const data = await response.clone().json();
    data['success'] && options['method'] !== 'GET' && message.success(data['message']);
  }
  return response;
});

export default request;

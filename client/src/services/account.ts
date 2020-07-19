import { request } from 'umi';

export default async function() {
  const {
    data: { username, nickname, realname },
  } = await request('/api/account', { method: 'GET' });
  const { data: menuData = [] } = await request('/api/authorities/tree', { method: 'GET' });
  return { username: realname || nickname || username, menuData };
}

import { request } from 'umi';

export default async function(params: object = {}) {
  return await request('/api/authenticate', { method: 'POST', data: params });
}

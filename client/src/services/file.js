import request from '@/utils/request';

export async function queryFile(params = {}) {
  return await request.get('/file', { params });
}

export async function queryFileByPageable(params = {}) {
  return await request.get('/file/pageable', { params });
}

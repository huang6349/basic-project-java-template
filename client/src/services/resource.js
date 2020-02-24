import request from '@/utils/request';

export async function createResource(resource = {}) {
  return await request.post('/resource', { data: resource });
}

export async function queryResource(params = {}) {
  return await request.get('/resource', { params });
}

export async function queryResourceByPageable(params = {}) {
  return await request.get('/resource/pageable', { params });
}

export async function updateResource(resource = {}) {
  return await request.put('/resource', { data: resource });
}

export async function deleteResource(id) {
  return await request.delete(`/resource/${id}`);
}

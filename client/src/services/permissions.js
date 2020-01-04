import request from '@/utils/request';

export async function createPermissions(permissions) {
  return await request.post('/permissions', { data: permissions });
}

export async function queryPermissions(params) {
  return await request.get('/permissions', { params });
}

export async function queryPermissionsToTree(params) {
  return await request.get('/permissions/tree', { params });
}

export async function queryPermissionsByPageable(params) {
  return await request.get('/permissions/pageable', { params });
}

export async function updatePermissions(permissions) {
  return await request.put('/permissions', { data: permissions });
}

export async function deletePermissions(id) {
  return await request.delete(`/permissions/${id}`);
}

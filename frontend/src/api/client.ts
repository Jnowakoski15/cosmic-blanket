import axios from 'axios';
import { UserManager } from 'oidc-client-ts';
import type { ApiError } from '@/types/common';

const client = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

const keycloakUrl = (window as any).__COSMIC_CONFIG__?.KEYCLOAK_URL || 'http://localhost:8180/realms/nova';

const userManager = new UserManager({
  authority: keycloakUrl,
  client_id: 'cosmic-frontend',
  redirect_uri: window.location.origin + '/',
  response_type: 'code',
  scope: 'openid profile email',
});

client.interceptors.request.use(async (config) => {
  try {
    const user = await userManager.getUser();
    if (user?.access_token && !user.expired) {
      config.headers.Authorization = `Bearer ${user.access_token}`;
    }
  } catch {
    // Continue without auth token
  }
  return config;
});

client.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      await userManager.removeUser();
    }
    if (error.response?.data) {
      const apiError: ApiError = error.response.data;
      return Promise.reject(apiError);
    }
    return Promise.reject({
      status: 500,
      message: error.message || 'Network error',
      timestamp: new Date().toISOString(),
      path: '',
    } as ApiError);
  }
);

export default client;

import request from './request'
import type { LoginRequest, RegisterRequest, LoginResponse } from '@/types/user'

export function login(data: LoginRequest) {
  return request.post<any, { code: number; data: LoginResponse }>('/auth/login', data)
}

export function register(data: RegisterRequest) {
  return request.post<any, { code: number; data: LoginResponse }>('/auth/register', data)
}

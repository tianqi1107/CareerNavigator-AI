import request from './request'

export function getDashboardData() {
  return request.get<any, any>('/dashboard')
}

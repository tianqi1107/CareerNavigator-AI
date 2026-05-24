import request from './request'
import type { JobSearchParams } from '@/types/job'

export function searchJobs(params: JobSearchParams) {
  return request.get<any, any>('/jobs', { params })
}

export function getJobDetail(id: number) {
  return request.get<any, any>(`/jobs/${id}`)
}

export function getHotJobs(limit = 10) {
  return request.get<any, any>('/jobs/hot', { params: { limit } })
}

export function getCategoryTree() {
  return request.get<any, any>('/categories/tree')
}

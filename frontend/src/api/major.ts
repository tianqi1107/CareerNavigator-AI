import request from './request'
import type { Major, MajorSearchParams } from '@/types/major'

export function searchMajors(params: MajorSearchParams) {
  return request.get<any, any>('/majors', { params })
}

export function getMajorDetail(id: number) {
  return request.get<any, any>(`/majors/${id}`)
}

export function getHotMajors(limit = 10) {
  return request.get<any, any>('/majors/hot', { params: { limit } })
}

export function getRelatedJobs(majorId: number) {
  return request.get<any, any>(`/majors/${majorId}/jobs`)
}

export interface Major {
  id: number
  majorCode: string
  majorName: string
  category?: string
  firstLevel?: string
  description?: string
  employmentDirection?: string
  avgSalary?: number
  employmentRate?: number
  hotScore: number
  relatedJobs?: string[]
}

export interface MajorSearchParams {
  keyword?: string
  page?: number
  size?: number
}

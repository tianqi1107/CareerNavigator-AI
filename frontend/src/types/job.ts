export interface Job {
  id: number
  jobName: string
  companyName?: string
  companyType?: string
  salaryMin?: number
  salaryMax?: number
  city?: string
  province?: string
  educationReq?: string
  experienceReq?: string
  skillsRequired?: string[]
  description?: string
  requirements?: string
  benefits?: string
  isCivilService?: number
  isStateOwned?: number
  publishDate?: string
  deadline?: string
  categoryName?: string
  viewCount?: number
  hotScore?: number
}

export interface JobSearchParams {
  keyword?: string
  categoryId?: number
  city?: string
  educationReq?: string
  companyType?: string
  page?: number
  size?: number
}

export interface JobCategory {
  id: number
  parentId: number
  name: string
  level: number
  icon?: string
  sortOrder: number
  description?: string
  children?: JobCategory[]
}

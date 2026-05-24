export interface CareerPlanRequest {
  major: string
  education: string
  skills?: string
  interests?: string
  city?: string
  targetCompanyType?: string
  additionalInfo?: string
}

export interface CareerPlanResponse {
  recommendedJobs?: string
  learningPath?: string
  skillGaps?: string
  gradSchoolAdvice?: string
  civilServiceAdvice?: string
  competitionAnalysis?: string
  overallAdvice?: string
  aiProvider?: string
}

export interface AiChatRequest {
  message: string
  provider?: string
}

export interface AiAnalysisRecord {
  id: number
  userId: number
  analysisType: string
  inputData?: string
  outputData?: string
  aiProvider?: string
  aiModel?: string
  tokensUsed?: number
  responseTime?: number
  createdAt?: string
}

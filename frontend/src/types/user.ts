export interface UserInfo {
  id: number
  username: string
  email?: string
  phone?: string
  nickname?: string
  avatar?: string
  role: string
  major?: string
  education?: string
  university?: string
  graduationYear?: number
  lastLoginTime?: string
  createdAt?: string
}

export interface LoginRequest {
  username: string
  password: string
}

export interface RegisterRequest {
  username: string
  password: string
  email?: string
  phone?: string
  nickname?: string
  major?: string
  education?: string
  university?: string
}

export interface LoginResponse {
  token: string
  user: UserInfo
}

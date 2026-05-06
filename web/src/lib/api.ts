function normalizeBaseUrl(url: string): string {
  return url.replace(/\/$/, '')
}

export function getApiBaseUrl(): string {
  const raw = import.meta.env.VITE_API_BASE_URL ?? ''
  if (raw) return normalizeBaseUrl(raw)
  return normalizeBaseUrl('/api')
}

export async function apiGet<T>(path: string): Promise<T> {
  const base = getApiBaseUrl()
  const p = path.startsWith('/') ? path : `/${path}`
  const res = await fetch(`${base}${p}`, { credentials: 'omit' })
  if (!res.ok) {
    throw new Error(`Request failed: ${res.status} ${res.statusText}`)
  }
  return res.json() as Promise<T>
}

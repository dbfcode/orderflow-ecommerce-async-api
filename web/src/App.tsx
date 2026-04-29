import { useCallback, useState } from 'react'

import { apiGet, getApiBaseUrl } from './lib/api'

function App() {
  const [result, setResult] = useState<string>('')
  const [loading, setLoading] = useState(false)

  const tryPing = useCallback(async () => {
    setLoading(true)
    setResult('')
    try {
      const data = await apiGet<{ status?: string }>('/test/ping')
      setResult(
        `OK: ${JSON.stringify(data)}`,
      )
    } catch (e) {
      setResult(e instanceof Error ? e.message : 'Erro desconhecido')
    } finally {
      setLoading(false)
    }
  }, [])

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100">
      <main className="mx-auto flex max-w-lg flex-col gap-6 px-6 py-16">
        <div>
          <h1 className="text-2xl font-semibold tracking-tight">
            OrderFlow Web
          </h1>
          <p className="mt-2 text-sm text-zinc-400">
            Boilerplate React + TypeScript + Tailwind. Base da API:{' '}
            <code className="rounded bg-zinc-800 px-1.5 py-0.5 text-xs">
              {getApiBaseUrl()}
            </code>
          </p>
        </div>
        <button
          type="button"
          onClick={tryPing}
          disabled={loading}
          className="w-fit rounded-lg bg-emerald-600 px-4 py-2 text-sm font-medium text-white hover:bg-emerald-500 disabled:opacity-60"
        >
          {loading ? 'Testando…' : 'Testar GET /test/ping'}
        </button>
        {result ? (
          <p className="rounded-lg border border-zinc-800 bg-zinc-900 px-4 py-3 text-sm text-zinc-300">
            {result}
          </p>
        ) : null}
      </main>
    </div>
  )
}

export default App

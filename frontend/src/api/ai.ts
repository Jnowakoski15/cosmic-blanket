import client from './client';

export interface ChatRequest {
  conversationId?: string;
  message: string;
}

export interface ChatResponse {
  conversationId: string;
  response: string;
  citations: Array<{
    source: string;
    content: string;
  }>;
}

export interface SearchResult {
  id: string;
  score: number;
  title: string;
  content: string;
  source: string;
}

export async function sendChatMessage(request: ChatRequest): Promise<ChatResponse> {
  const { data } = await client.post('/ai/chat', request);
  return data;
}

export async function search(query: string, limit = 10): Promise<SearchResult[]> {
  const { data } = await client.get('/ai/search', { params: { q: query, limit } });
  return data;
}

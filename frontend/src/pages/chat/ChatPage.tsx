import { useState } from 'react';
import type { ChatResponse } from '@/api/ai';
import { sendChatMessage } from '@/api/ai';

interface Message {
  role: 'user' | 'assistant';
  content: string;
  citations?: Array<{ source: string; content: string }>;
}

export default function ChatPage() {
  const [messages, setMessages] = useState<Message[]>([]);
  const [input, setInput] = useState('');
  const [conversationId, setConversationId] = useState<string | undefined>();
  const [isLoading, setIsLoading] = useState(false);

  const handleSend = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!input.trim() || isLoading) return;

    const userMessage = input.trim();
    setInput('');
    setMessages((prev) => [...prev, { role: 'user', content: userMessage }]);
    setIsLoading(true);

    try {
      const response: ChatResponse = await sendChatMessage({
        conversationId,
        message: userMessage,
      });
      setConversationId(response.conversationId);
      setMessages((prev) => [
        ...prev,
        { role: 'assistant', content: response.response, citations: response.citations },
      ]);
    } catch {
      setMessages((prev) => [
        ...prev,
        { role: 'assistant', content: 'Sorry, I encountered an error. Please try again.' },
      ]);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: '800px', margin: '2rem auto', padding: '0 2rem', display: 'flex', flexDirection: 'column', height: 'calc(100vh - 200px)' }}>
      <h1 style={{ fontSize: '1.75rem', color: '#1a365d', marginBottom: '1rem' }}>AI Assistant</h1>
      <p style={{ color: '#6b7280', marginBottom: '1.5rem', fontSize: '0.9rem' }}>
        Ask me anything about State of Nova government services.
      </p>

      <div style={{ flex: 1, overflowY: 'auto', display: 'flex', flexDirection: 'column', gap: '1rem', marginBottom: '1rem' }}>
        {messages.length === 0 && (
          <div style={{ textAlign: 'center', color: '#9ca3af', padding: '3rem' }}>
            Start a conversation by typing a question below.
          </div>
        )}
        {messages.map((msg, i) => (
          <div
            key={i}
            style={{
              alignSelf: msg.role === 'user' ? 'flex-end' : 'flex-start',
              backgroundColor: msg.role === 'user' ? '#1a365d' : '#f1f5f9',
              color: msg.role === 'user' ? 'white' : '#1e293b',
              padding: '0.75rem 1rem',
              borderRadius: '1rem',
              maxWidth: '80%',
              fontSize: '0.9rem',
              lineHeight: 1.5,
            }}
          >
            {msg.content}
          </div>
        ))}
        {isLoading && (
          <div style={{ alignSelf: 'flex-start', color: '#9ca3af', padding: '0.75rem', fontStyle: 'italic' }}>
            Thinking...
          </div>
        )}
      </div>

      <form onSubmit={handleSend} style={{ display: 'flex', gap: '0.5rem' }}>
        <input
          style={{
            flex: 1,
            padding: '0.75rem',
            border: '1px solid #d1d5db',
            borderRadius: '0.5rem',
            fontSize: '0.9rem',
          }}
          placeholder="Ask about state services..."
          value={input}
          onChange={(e) => setInput(e.target.value)}
          disabled={isLoading}
        />
        <button
          type="submit"
          disabled={isLoading || !input.trim()}
          style={{
            backgroundColor: '#1a365d',
            color: 'white',
            padding: '0.75rem 1.5rem',
            borderRadius: '0.5rem',
            border: 'none',
            cursor: isLoading ? 'not-allowed' : 'pointer',
            opacity: isLoading ? 0.7 : 1,
          }}
        >
          Send
        </button>
      </form>
    </div>
  );
}

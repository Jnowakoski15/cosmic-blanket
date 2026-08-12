import { createBrowserRouter } from 'react-router-dom';
import App from './App';
import HomePage from './pages/HomePage';
import ApplicationListPage from './pages/licensing/ApplicationListPage';
import NewApplicationPage from './pages/licensing/NewApplicationPage';
import ApplicationDetailPage from './pages/licensing/ApplicationDetailPage';
import RequestListPage from './pages/vital-records/RequestListPage';
import PropertySearchPage from './pages/property-tax/PropertySearchPage';
import ChatPage from './pages/chat/ChatPage';
import SearchPage from './pages/search/SearchPage';
import NotFoundPage from './pages/NotFoundPage';

export const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <HomePage /> },
      { path: 'licensing', element: <ApplicationListPage /> },
      { path: 'licensing/new', element: <NewApplicationPage /> },
      { path: 'licensing/applications/:id', element: <ApplicationDetailPage /> },
      { path: 'vital-records', element: <RequestListPage /> },
      { path: 'property-tax', element: <PropertySearchPage /> },
      { path: 'chat', element: <ChatPage /> },
      { path: 'search', element: <SearchPage /> },
      { path: '*', element: <NotFoundPage /> },
    ],
  },
]);

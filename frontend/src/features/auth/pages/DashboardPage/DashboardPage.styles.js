import styled from 'styled-components';

export const DashboardContainer = styled.div`
  padding: 2rem;
  max-width: 800px;
  margin: 0 auto;
`;

export const UserInfoCard = styled.div`
  padding: 1.5rem;
  margin: 2rem 0;
  background: ${({ theme }) => theme.palette.background.paper};
  border-radius: ${({ theme }) => theme.shape.borderRadius}px;
  box-shadow: ${({ theme }) => theme.shadows[1]};
`;

export const LogoutButton = styled.button`
  padding: 0.75rem 1.5rem;
  background: ${({ theme }) => theme.palette.error.main};
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.3s;

  &:hover {
    background: ${({ theme }) => theme.palette.error.dark};
  }
`;
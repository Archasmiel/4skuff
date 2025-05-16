import styled from 'styled-components';

export const DashboardContainer = styled.div`
  padding: 2rem;
`;

export const UserInfoCard = styled.div`
  padding: 1.5rem;
  margin-bottom: 2rem;
  background: ${({ theme }) => theme.palette.background.paper};
  border-radius: ${({ theme }) => theme.shape.borderRadius}px;
  box-shadow: ${({ theme }) => theme.shadows[1]};
`;

export const LogoutButton = styled.button`
  padding: 0.5rem 1rem;
  background: ${({ theme }) => theme.palette.error.main};
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  &:hover {
    background: ${({ theme }) => theme.palette.error.dark};
  }
`;
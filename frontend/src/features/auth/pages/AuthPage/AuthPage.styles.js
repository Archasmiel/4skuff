import styled from 'styled-components';

export const AuthContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 2rem;
`;

export const AuthToggle = styled.div`
  margin-top: 1rem;
  color: ${({ theme }) => theme.palette.primary.main};
  cursor: pointer;
  text-decoration: underline;
  &:hover {
    color: ${({ theme }) => theme.palette.primary.dark};
  }
`;
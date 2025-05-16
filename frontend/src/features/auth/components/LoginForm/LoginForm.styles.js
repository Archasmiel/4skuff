import styled from 'styled-components';

export const FormContainer = styled.div`
  width: 100%;
  max-width: 400px;
  padding: 2rem;
  background: ${({ theme }) => theme.palette.background.paper};
  border-radius: ${({ theme }) => theme.shape.borderRadius}px;
  box-shadow: ${({ theme }) => theme.shadows[2]};
`;

export const ErrorText = styled.div`
  color: ${({ theme }) => theme.palette.error.main};
  margin-bottom: 1rem;
`;
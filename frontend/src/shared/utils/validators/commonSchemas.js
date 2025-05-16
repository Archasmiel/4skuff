import * as Yup from 'yup';

export const usernameSchema = Yup.string()
  .min(3, 'Username must be at least 3 characters')
  .max(20, 'Username must not exceed 20 characters')
  .required('Username is required');

export const emailSchema = Yup.string()
  .email('Invalid email address')
  .required('Email is required');

export const passwordSchema = Yup.string()
  .min(6, 'Password must be at least 6 characters')
  .required('Password is required');
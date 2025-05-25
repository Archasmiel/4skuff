import { useFormik } from 'formik';
import { Button, TextField, Box, Typography } from '@mui/material';
import loginValidationSchema from './loginValidationSchema';
import { FormContainer } from './LoginForm.styles';

const LoginForm = ({ onSubmit, isLoading }) => {
  const formik = useFormik({
    initialValues: {
      username: '',
      password: '',
    },
    loginValidationSchema,
    onSubmit: (values) => {
      onSubmit(values);
    },
  });

  return (
    <FormContainer>
      <form onSubmit={formik.handleSubmit}>
        <TextField
          fullWidth
          id="username"
          name="username"
          label="Username"
          value={formik.values.username}
          onChange={formik.handleChange}
          error={formik.touched.username && Boolean(formik.errors.username)}
          helperText={formik.touched.username && formik.errors.username}
          margin="normal"
        />
        <TextField
          fullWidth
          id="password"
          name="password"
          label="Password"
          type="password"
          value={formik.values.password}
          onChange={formik.handleChange}
          error={formik.touched.password && Boolean(formik.errors.password)}
          helperText={formik.touched.password && formik.errors.password}
          margin="normal"
        />
        <Box mt={2}>
          <Button
            color="primary"
            variant="contained"
            fullWidth
            type="submit"
            disabled={isLoading}
          >
            {isLoading ? 'Loading...' : 'Login'}
          </Button>
        </Box>
      </form>
    </FormContainer>
  );
};

export default LoginForm;
import * as Yup from 'yup';
import { usernameSchema, emailSchema, passwordSchema } from '../../../../shared/utils/validators/commonSchemas';

const registerValidationSchema = Yup.object().shape({
  username: usernameSchema,
  email: emailSchema,
  password: passwordSchema,
});

export default registerValidationSchema;
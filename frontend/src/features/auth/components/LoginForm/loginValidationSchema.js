import * as Yup from 'yup';
import { usernameSchema, passwordSchema } from '../../../../shared/utils/validators/commonSchemas';

const loginValidationSchema = Yup.object().shape({
  username: usernameSchema,
  password: passwordSchema,
});

export default loginValidationSchema;
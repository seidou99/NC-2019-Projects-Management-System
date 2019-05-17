export const validationConfigs = {
  password: {
    minlength: 6,
    maxlength: 20
  },
  name: {
    minlength: 3,
    maxlength: 20
  },
  surname: {
    minlength: 3,
    maxlength: 20
  },
  projectCode: {
    minlength: 3,
    maxlength: 5
  },
  projectSummary: {
    minlength: 20,
    maxlength: 200
  },
  taskDescription: {
    minlength: 20,
    maxlength: 600
  },
  estimation: {
    min: 1
  },
  comment: {
    minlength: 3,
    maxlength: 400
  }
};

const serverURI = 'http://localhost:8081';
const apiURI = serverURI + '/api';
export const loginURI = apiURI + '/login';
export const usersURI = apiURI + '/users';
export const projectsURI = apiURI + '/projects';

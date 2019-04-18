export const validationConfigs = {
  password: {
    minlength: 3,
    maxlength: 20
  },
  name: {
    minlength: 2,
    maxlength: 20
  },
  surname: {
    minlength: 2,
    maxlength: 20
  },
  projectCode: {
    minlength: 3,
    maxlength: 6
  },
  projectSummary: {
    minlength: 5,
    maxlength: 30
  },
  taskDescription: {
    minlength: 10,
    maxlength: 200
  },
  estimation: {
    min: 1
  }
};

const serverURI = 'http://localhost:8081';
const apiURI = serverURI + '/api';
export const loginURI = apiURI + '/login';
export const usersURI = apiURI + '/users';
export const projectsURI = apiURI + '/projects';

export const validationConfigs = {
  password: {
    minlength: 3,
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
    maxlength: 6
  },
  projectSummary: {
    minlength: 10,
    maxlength: 200
  },
  taskDescription: {
    minlength: 10,
    maxlength: 200
  }
};

const serverURI = 'http://localhost:8081';
const apiURI = serverURI + '/api';
export const usersURI = apiURI + '/users';
export const tasksURI = apiURI + '/tasks';
export const projectsURI = apiURI + '/projects';

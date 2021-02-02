# course-app

##CourseController:

- GET /course/{id}
- GET /courses
- GET /courses/{date}
- GET /courses/{tags}  (/courses/tag1,tag2,tag3)
- GET /courses/{name}

- POST /course
- DELETE /course/{id}
- PUT /course/{id}

##TaskController
- GET /task/{id}
- GET /tasks/{course}
- GET /tasks/{status}

- POST /task
- PUT /task/{id}
- DELETE /task/{id}


##TagController
- GET /tag/{id}
- GET /tags
- GET /tags/{name}
- GET /tags/{course}

- POST /tag
- DELETE /tag/{id}
- PUT /tag/{id}


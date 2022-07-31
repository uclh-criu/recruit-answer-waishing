FROM node:carbon
USER node
# Create app directory
WORKDIR /app

COPY . /app

# Copy the package.json as well as the package-lock.json and install
# the dependencies. This is a separate step so the dependencies
# will be cached unless changes to one of those two files
# are made.
COPY package*.json /app
RUN npm install

# Copy the main application

# Arguments
ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}

#RUN npm run build

EXPOSE 3000
CMD ["npm", "start"]
FROM node:18-alpine as base
WORKDIR /usr/local/app
COPY package*.json ./
RUN npm install
COPY ./ ./
EXPOSE 3000
CMD [ "npm", "start" ]
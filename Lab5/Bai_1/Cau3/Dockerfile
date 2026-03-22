FROM node:20-slim as base
WORKDIR /usr/local/app
COPY package*.json ./
RUN npm install
COPY ./ ./
EXPOSE 5173
CMD [ "npm", "run", "dev" ]
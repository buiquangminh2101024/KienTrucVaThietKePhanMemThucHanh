FROM node:lts-alpine
WORKDIR /app
COPY package*.json ./
RUN npm i
COPY ./ ./
# Nếu định nghĩa port của docker-compose rồi thì không cần expose ở đây cũng được
EXPOSE 3000
CMD [ "npm", "start" ]
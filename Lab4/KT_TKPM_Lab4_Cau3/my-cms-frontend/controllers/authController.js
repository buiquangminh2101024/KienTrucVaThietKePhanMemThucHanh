const BACKEND_URL = 'http://localhost:8080'; // Assuming backend runs on port 8080

exports.showRegister = (req, res) => {
  res.render('register');
};

exports.register = async (req, res) => {
  const { username, password, email } = req.body;
  try {
    const response = await fetch(`${BACKEND_URL}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password, email })
    });
    const data = await response.json();
    if (response.ok) {
      res.redirect('/login');
    } else {
      res.render('register', { error: data.message });
    }
  } catch (error) {
    res.render('register', { error: 'Error connecting to backend' });
  }
};

exports.showLogin = (req, res) => {
  res.render('login');
};

exports.login = async (req, res) => {
  const { username, password } = req.body;
  try {
    const response = await fetch(`${BACKEND_URL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include', // Include cookies in request
      body: JSON.stringify({ username, password })
    });
    const data = await response.json();
    if (response.ok) {
      // Store token or session
      res.cookie('token', data.token); // Assuming backend returns token
      res.redirect('/content');
    } else {
      res.render('login', { error: data.message });
    }
  } catch (error) {
    res.render('login', { error: 'Error connecting to backend' });
  }
};
const BACKEND_URL = 'http://localhost:8080';

exports.showAddContent = (req, res) => {
  res.render('addContent');
};

exports.addContent = async (req, res) => {
  const { title, body } = req.body;
  const token = req.cookies.token; // Assuming token is stored in cookie
  try {
    const response = await fetch(`${BACKEND_URL}/contents`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-Username': req.body.username, // Assuming username from form
        'X-Password': req.body.password
      },
      body: JSON.stringify({ title, body })
    });
    const data = await response.json();
    if (response.ok) {
      res.redirect('/content');
    } else {
      res.render('addContent', { error: data.message });
    }
  } catch (error) {
    res.render('addContent', { error: 'Error connecting to backend' });
  }
};

exports.showContent = async (req, res) => {
  try {
    const response = await fetch(`${BACKEND_URL}/contents`);
    const contents = await response.json();
    res.render('viewContent', { contents });
  } catch (error) {
    res.render('viewContent', { contents: [], error: 'Error loading content' });
  }
};
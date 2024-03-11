app.get('/api/profile', (req, res) => {
    const userId = req.query.userId;
    // No validation or authorization check
    const user = database.getUserById(userId);
    if (user) {
        res.json(user);
    } else {
        res.status(404).send('User not found');
    }
});

const helmet = require('helmet');
app.use(helmet());

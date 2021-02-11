## Branching flow
* **master** : le rendu de la semaine. Stable. Des hotfix peuvent être soumis.
* **pre-render** : une version plutôt stable du rendu qui sera testé sur le site. S'il passe, il sera merge sur le master .
* **develop** : version instable du rendu. S'il semble stable, on le merge sur le pre-render .
* **feature/name** : une fonctionnalité. S'il marche bien, on le merge sur le develop .
